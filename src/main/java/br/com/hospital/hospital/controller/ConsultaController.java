package br.com.hospital.hospital.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import br.com.hospital.hospital.entity.Consulta;
import br.com.hospital.hospital.repository.AtendimentoRepository;
import br.com.hospital.hospital.service.AtendimentoService;
import br.com.hospital.hospital.service.ConsultaService;
import br.com.hospital.hospital.service.MedicoService;
import br.com.hospital.hospital.service.PacienteService;

@Controller
@RequestMapping("/consultas")
public class ConsultaController {

    @Autowired
    private ConsultaService consultaService;

    @Autowired
    private AtendimentoService atendimentoService;

    @Autowired
    private PacienteService pacienteService;

    @Autowired
    private MedicoService medicoService;

    @Autowired
    private AtendimentoRepository atendimentoRepository;


    //Salvar
    @PostMapping("/salvar")
    public String salvar(@ModelAttribute Consulta consulta) {
        consultaService.save(consulta);
        return "redirect:/consultas/listar";
    }

    //Listar
    @GetMapping("/listar")
    public String listar(Model model) {
        List<Consulta> consultas = consultaService.findAll();
        model.addAttribute("consultas", consultas);
        return "consulta/listaConsulta";
    }

    //Criar
    @GetMapping("/criar")
    public String criarform(Model model) {
        model.addAttribute("consulta", new Consulta());
        model.addAttribute("pacientes", pacienteService.findAll());
        model.addAttribute("medicos", medicoService.findAll());
        return "consulta/formularioConsulta";
    }

    //Excluir
    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable("id") Integer id) {
         atendimentoService.excluirAtendimentosDaConsulta(id);
        consultaService.deleteById(id);
        return "redirect:/consultas/listar";
    }

    //Editar
    @GetMapping("/editar/{id}")
    public String editarForm(@PathVariable("id") Integer id, Model model) {
        Consulta consulta = consultaService.findById(id);
        model.addAttribute("consulta", consulta);
        model.addAttribute("pacientes", pacienteService.findAll());
        model.addAttribute("medicos", medicoService.findAll());
        return "consulta/formularioConsulta";
    }
}
