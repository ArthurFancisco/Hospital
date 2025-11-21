package br.com.hospital.hospital.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import br.com.hospital.hospital.entity.Consulta;
import br.com.hospital.hospital.service.ConsultaService;
import br.com.hospital.hospital.service.MedicoService;
import br.com.hospital.hospital.service.PacienteService;

import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequestMapping("/consultas")
public class ConsultaController{

    @Autowired
    private ConsultaService consultaService;
    @Autowired
    private PacienteService pacienteService;
    @Autowired
    private MedicoService medicoService;
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
    public String excluir(@PathVariable Integer id) {
        consultaService.deleteById(id);
        return "redirect:/consultas/listar";
    }

    //Editar
    @GetMapping("/editar/{id}")
    public String editarForm(@PathVariable Integer id, Model model) {
        Consulta consulta = consultaService.findById(id);
        model.addAttribute("consulta", consulta);
        model.addAttribute("pacientes", pacienteService.findAll());
    model.addAttribute("medicos", medicoService.findAll());
        return "consulta/formularioConsulta";
    }

}
