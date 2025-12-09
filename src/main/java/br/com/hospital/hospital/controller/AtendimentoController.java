package br.com.hospital.hospital.controller;

import br.com.hospital.hospital.entity.Atendimento;
import br.com.hospital.hospital.entity.Consulta;
import br.com.hospital.hospital.service.AtendimentoService;
import br.com.hospital.hospital.service.ConsultaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
public class AtendimentoController {

    @Autowired
    private ConsultaService consultaService;

    @Autowired
    private AtendimentoService atendimentoService;

    @GetMapping("/atendimento/iniciar/{id}")
public String iniciarAtendimento(@PathVariable("id") Integer id, Model model, RedirectAttributes redirectAttributes) {

    Optional<Consulta> optionalConsulta = consultaService.buscarConsultaPorId(id);

    if (optionalConsulta.isEmpty()) {
        redirectAttributes.addFlashAttribute("mensagemErro", "Consulta não encontrada.");
        return "redirect:/consultas/listar";
    }

    Consulta consulta = optionalConsulta.get();

    Atendimento atendimento = new Atendimento();
    atendimento.setIdConsulta(consulta); // ← Aqui está o ajuste

    model.addAttribute("consulta", consulta);
    model.addAttribute("atendimento", atendimento);

    return "medicoHome/formularioAtendimento";
}

    @PostMapping("/atendimento/salvar")
    public String salvarAtendimento(Atendimento atendimento, RedirectAttributes redirectAttributes) {

    if (atendimento.getIdConsulta() == null || atendimento.getIdConsulta().getIdConsulta() == null) {
        redirectAttributes.addFlashAttribute("mensagemErro", "ID da consulta é obrigatório.");
        return "redirect:/consultas/listar";
    }

    try {
        // Salva o atendimento
        atendimentoService.salvarAtendimento(atendimento);

        // Atualiza o status da consulta associada
        consultaService.atualizarStatusConsulta(
            atendimento.getIdConsulta().getIdConsulta(),
            "ATENDIDA"
        );

        redirectAttributes.addFlashAttribute("mensagemSucesso",
                "Atendimento salvo e consulta concluída!");
    } catch (Exception e) {
        redirectAttributes.addFlashAttribute("mensagemErro",
                "Erro ao salvar o atendimento: " + e.getMessage());
    }

    return "redirect:/consultas/listar";
}
}
