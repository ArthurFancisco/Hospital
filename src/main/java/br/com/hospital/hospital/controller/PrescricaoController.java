package br.com.hospital.hospital.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import br.com.hospital.hospital.entity.Prescricao;
import br.com.hospital.hospital.repository.ConsultaRepository;
import br.com.hospital.hospital.service.PrescricaoService;
import br.com.hospital.hospital.service.ConsultaService;
import br.com.hospital.hospital.service.InternacaoService;
import br.com.hospital.hospital.service.MedicamentoService;

@Controller
@RequestMapping("/prescricoes")
public class PrescricaoController {

    @Autowired
    private PrescricaoService prescricaoService;
    @Autowired
    private ConsultaService consultaService;
    @Autowired
    private InternacaoService internacaoService;
    @Autowired
    private MedicamentoService medicamentoService;
    @Autowired
private ConsultaRepository consultaRepository;

    // Listar
    @GetMapping("/listar")
    public String listar(Model model) {
        List<Prescricao> prescricoes = prescricaoService.findAll();
        model.addAttribute("prescricoes", prescricoes);
        return "prescricao/listaPrescricao";
    }

    // Criar
    @GetMapping("/criar")
    public String criarForm(Model model) {
        model.addAttribute("prescricao", new Prescricao());
        model.addAttribute("consultas", consultaService.findAll());
        model.addAttribute("internacoes", internacaoService.findAll());
        model.addAttribute("medicamentos", medicamentoService.findAll());
        return "prescricao/formularioPrescricao";
    }

    // Salvar
    @PostMapping("/salvar")
    public String salvar(@ModelAttribute Prescricao prescricao, Model model) {
        
        // 1. Verificar se o objeto Consulta foi preenchido no formulário (imagem: image_fad249.png)
        if (prescricao.getConsulta() != null && prescricao.getConsulta().getIdConsulta() != null) {
            
            Integer consultaId = prescricao.getConsulta().getIdConsulta();
            
            // 2. Buscar a Consulta COMPLETA no banco de dados para garantir que o Paciente está carregado.
            // O ConsultaRepository já está injetado na linha 25.
            consultaRepository.findById(consultaId).ifPresent(consultaCompleta -> {
                
                // 3. ⭐️ LINHA CRÍTICA: Associar o Paciente da Consulta à Prescrição ⭐️
                // Isso resolve o erro 'not-null property references a null value: Prescricao.paciente'
                prescricao.setPaciente(consultaCompleta.getPaciente());
                
                // 4. Salvar a Prescrição agora completa (com Paciente e Consulta associados)
                prescricaoService.save(prescricao);
            });
            
        } else {
            return "redirect:/prescricoes/criar?error=consulta_necessaria";
        }
        
        return "redirect:/prescricoes/listar";
    }

    // Editar
    @GetMapping("/editar/{id}")
    public String editarForm(@PathVariable Integer id, Model model) {
        Prescricao prescricao = prescricaoService.findById(id);
        model.addAttribute("prescricao", prescricao);
        model.addAttribute("consultas", consultaService.findAll());
        model.addAttribute("internacoes", internacaoService.findAll());
        model.addAttribute("medicamentos", medicamentoService.findAll());
        return "prescricao/formularioPrescricao";
    }

    // Excluir
    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable Integer id) {
        prescricaoService.deleteById(id);
        return "redirect:/prescricoes/listar";
    }
}
