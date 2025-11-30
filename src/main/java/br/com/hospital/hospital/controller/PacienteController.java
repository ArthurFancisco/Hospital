package br.com.hospital.hospital.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.hospital.hospital.DTO.PacienteCadastroDTO;
import br.com.hospital.hospital.entity.Consulta;
import br.com.hospital.hospital.entity.Paciente;
import br.com.hospital.hospital.repository.ConsultaRepository;
import br.com.hospital.hospital.repository.PacienteRepository;
import br.com.hospital.hospital.service.PacienteService;
import jakarta.servlet.http.HttpSession;

@Controller
public class PacienteController {

    @Autowired
    private PacienteService pacienteService;
    @Autowired
    private ConsultaRepository consultaRepository;
    @Autowired
    private PacienteRepository pacienteRepository;

    // --------------------------------------------------------
    // MÉTODOS AUXILIARES E DE ACESSO DO PACIENTE LOGADO
    // --------------------------------------------------------

    /**
     * Método auxiliar para buscar o Paciente pelo ID da sessão e validar o Role.
     * Retorna o Paciente ou null se a sessão for inválida.
     */
    private Paciente getPacienteLogado(HttpSession session) {
        // Busca o ID e o Role que foram salvos no LoginController
        Integer pacienteId = (Integer) session.getAttribute("pacienteId");
        String userRole = (String) session.getAttribute("userRole");

        // ⭐️ Ponto de Validação
        if (pacienteId == null || !"PACIENTE".equals(userRole)) {
            return null; 
        }

        // Busca o Paciente completo no banco
        Optional<Paciente> pacienteOpt = pacienteRepository.findById(pacienteId); 
        return pacienteOpt.orElse(null);
    }
    
    // Rota Home do Paciente (Dashboard)
    @GetMapping("/pacienteHome")
    public String pacienteHome(HttpSession session, Model model) {
        Paciente paciente = getPacienteLogado(session);

        if (paciente == null) {
            return "redirect:/login"; // Redireciona se não estiver logado
        }
        
        model.addAttribute("pacienteNome", paciente.getNomePaciente()); 
        
        return "/pacienteHome"; // Retorna o template do dashboard bonito
    }

    // Rota para Minhas Consultas
    @GetMapping("/minhasConsultas") 
    public String minhasConsultas(HttpSession session, Model model) {

        Paciente pacienteLogado = getPacienteLogado(session);

        if (pacienteLogado == null) {
            return "redirect:/login"; // Redireciona se não estiver logado
        }
        
        // Busca as consultas usando o objeto Paciente logado
        List<Consulta> consultas = consultaRepository.findByPaciente(pacienteLogado); 
        
        model.addAttribute("listaDeConsultas", consultas);

        return "pacienteHome/consultasPaciente"; 
    }

    // Rota para Meus Dados Cadastrais
    @GetMapping("/pacientes/meusdados")
    public String meusDados(HttpSession session, Model model) {
        Paciente pacienteLogado = getPacienteLogado(session);

        if (pacienteLogado == null) {
            return "redirect:/login"; 
        }

        // Adiciona o objeto Paciente completo ao modelo
        model.addAttribute("paciente", pacienteLogado);
        
        return "pacienteHome/dadosPaciente"; 
    }

    // --------------------------------------------------------
    // MÉTODOS ADMINISTRATIVOS (CRUD BÁSICO)
    // --------------------------------------------------------

    // Rota Admin para exibir o formulário de CRIAÇÃO (USA DTO)
    @GetMapping("/pacientes/criar")
    public String criarform(Model model) {
        model.addAttribute("pacienteDTO", new PacienteCadastroDTO());
        return "paciente/formularioPaciente"; 
    }

    // Editar (usa a Entity Paciente)
    @GetMapping("/pacientes/editar/{id}")
    public String editarForm(@PathVariable Integer id, Model model) {
        Optional<Paciente> paciente = pacienteService.findById(id); 
        model.addAttribute("paciente", paciente.orElse(new Paciente())); 
        return "paciente/formularioPaciente"; 
    }
    
    // Salvar/Atualizar (Método POST Admin para Entity)
    @PostMapping("/pacientes/salvar")
    public String salvar(@ModelAttribute Paciente paciente) {
        pacienteService.save(paciente);
        return "redirect:/pacientes/listar";
    }

    // Listar
    @GetMapping("/pacientes/listar")
    public String listar(Model model) {
        List<Paciente> pacientes = pacienteService.findAll();
        model.addAttribute("pacientes", pacientes);
        return "paciente/listaPaciente";
    }

    // Excluir
    @GetMapping("/pacientes/excluir/{id}")
    public String excluir(@PathVariable Integer id) {
        pacienteService.deleteById(id);
        return "redirect:/pacientes/listar";
    }

    // --------------------------------------------------------
    // CADASTRO INTEGRADO (USUÁRIO + PACIENTE)
    // --------------------------------------------------------
    
    // Rota GET para exibir o formulário de cadastro integrado
    @GetMapping("/cadastroPaciente") 
    public String mostrarFormularioCadastro(Model model) {
        model.addAttribute("pacienteDTO", new PacienteCadastroDTO()); 
        return "paciente/formularioPaciente";
    }

    // Rota POST para salvar o cadastro integrado
    @PostMapping("/pacientes/cadastrar") 
    public String salvarPaciente(@ModelAttribute("pacienteDTO") PacienteCadastroDTO dto, RedirectAttributes ra) {
        try {
            pacienteService.cadastrarNovoPaciente(dto); 
            ra.addFlashAttribute("mensagemSucesso", "Paciente e Acesso criados com sucesso!");
            return "redirect:/pacientes/listar"; 
        } catch (Exception e) {
            System.err.println("Erro ao salvar: " + e.getMessage()); 
            ra.addFlashAttribute("mensagemErro", "Erro ao cadastrar: " + e.getMessage());
            return "redirect:/cadastroPaciente"; 
        }
    }
}