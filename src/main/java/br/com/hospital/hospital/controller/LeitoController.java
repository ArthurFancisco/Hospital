package br.com.hospital.hospital.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.hospital.hospital.entity.Leito;
import br.com.hospital.hospital.service.LeitoService;

@Controller
@RequestMapping("/leitos")
public class LeitoController {

    @Autowired
    private LeitoService leitoService;

    // --- SALVAR/ATUALIZAR ---
    @PostMapping("/salvar")
    public String salvar(@ModelAttribute Leito leito, RedirectAttributes ra) {
        try {
            leitoService.save(leito);
            // Mensagem de sucesso
            String mensagem = (leito.getIdQuarto() == null) ? "Leito cadastrado com sucesso!" : "Leito atualizado com sucesso!";
            ra.addFlashAttribute("mensagemSucesso", mensagem);
        } catch (Exception e) {
            // Mensagem de erro em caso de falha no DB ou Service
            ra.addFlashAttribute("mensagemErro", "Erro ao salvar o leito: " + e.getMessage());
            // Retorna ao formulário para corrigir (mantendo os dados)
            return "leito/formularioLeito"; 
        }
        return "redirect:/leitos/listar";
    }

    // --- LISTAR ---
    @GetMapping("/listar")
    public String listar(Model model) {
        List<Leito> leitos = leitoService.findAll();
        model.addAttribute("leitos", leitos);
        // Mensagens de sucesso/erro vindas de RedirectAttributes são lidas aqui
        return "leito/listaLeito";
    }

    // --- CRIAR (FORMULÁRIO VAZIO) ---
    @GetMapping("/criar")
    public String criarForm(Model model) {
        model.addAttribute("leito", new Leito());
        return "leito/formularioLeito";
    }

    // --- EXCLUIR ---
    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable Integer id, RedirectAttributes ra) {
        try {
            leitoService.deleteById(id);
            ra.addFlashAttribute("mensagemSucesso", "Leito ID " + id + " excluído com sucesso!");
        } catch (Exception e) {
            // Erro se o leito estiver em uso (chave estrangeira)
            ra.addFlashAttribute("mensagemErro", "Erro ao excluir o leito: O leito pode estar referenciado por uma Internação.");
        }
        return "redirect:/leitos/listar";
    }

    // --- EDITAR (CARREGAR FORMULÁRIO) ---
    @GetMapping("/editar/{id}")
    public String editarForm(@PathVariable Integer id, Model model, RedirectAttributes ra) {
        try {
            Leito leito = leitoService.findById(id); 
            
            if (leito == null) {
                ra.addFlashAttribute("mensagemErro", "Leito ID " + id + " não encontrado.");
                return "redirect:/leitos/listar";
            }
            
            model.addAttribute("leito", leito);
            return "leito/formularioLeito";
            
        } catch (Exception e) {
            ra.addFlashAttribute("mensagemErro", "Erro ao buscar o leito para edição.");
            return "redirect:/leitos/listar";
        }
    }
}