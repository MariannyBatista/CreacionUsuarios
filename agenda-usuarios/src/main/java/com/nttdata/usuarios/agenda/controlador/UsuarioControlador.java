package com.nttdata.usuarios.agenda.controlador;

import java.util.List;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.nttdata.usuarios.agenda.modelo.Usuario;
import com.nttdata.usuarios.agenda.repositorio.UsuarioRepositorio;


@Controller
public class UsuarioControlador {

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;
    
    @GetMapping({"/",""})
    public String verPaginaDeInicio(Model modelo) {
        List<Usuario> usuarios = usuarioRepositorio.findAll();
        modelo.addAttribute("usuarios",usuarios);
        return "index.html";
    }
    
    @GetMapping("/nuevo")
    public String mostrarFormularioDeRegistroUsuario(Model modelo) {
        modelo.addAttribute("usuario", new Usuario());
        return "nuevo";
    }
    
    @PostMapping("/nuevo")
    public String guardarUsuario(@Validated Usuario usuario, BindingResult bindingResult, RedirectAttributes redirect, Model modelo) { 
        if(bindingResult.hasErrors()) {
        	modelo.addAttribute("usuario", usuario);
            return "nuevo";
        }
        
        usuarioRepositorio.save(usuario);
        redirect.addFlashAttribute("msgExito","El usuario ha sido registrado con exito.");
        return "redirect:/";
    }
    
    
	/*
	 * @GetMapping("/{id}/editar") public String
	 * mostrarFormularioDeEditarUsuario(@PathVariable Integer id,Model modelo) {
	 * Usuario usuario = usuariooRepositorio.getById(id);
	 * modelo.addAttribute("usuario", usuario); return "nuevo"; }
	 * 
	 * @PostMapping("/{id}/editar") public String actualizarUsuario(@PathVariable
	 * Integer id,@Validated Usuario usuario,BindingResult
	 * bindingResult,RedirectAttributes redirect,Model modelo) { Usuario usuarioDB
	 * = usuarioRepositorio.getById(id); if(bindingResult.hasErrors()) {
	 * modelo.addAttribute("usuario", usuario); return "nuevo"; }
	 * 
	 * usuarioDB.setNombre(usuario.getNombre());
	 * usuarioDB.setCelular(usuario.getCelular());
	 * usuarioDB.setEmail(usuario.getEmail());
	 * usuarioDB.setFechaNacimiento(usuario.getFechaNacimiento());
	 * usuarioDB.setPassword(usuario.getPassword());
	 * 
	 * 
	 * usuarioRepositorio.save(usuarioDB); redirect.addFlashAttribute("msgExito",
	 * "El usuario ha sido actualizado correctamente"); return "redirect:/"; }
	 * 
	 * @PostMapping("/{id}/eliminar") public String eliminarUsuario(@PathVariable
	 * Integer id,RedirectAttributes redirect) { Usuario usuario =
	 * usuarioRepositorio.getById(id); usuarioRepositorio.delete(usuario);
	 * redirect.addFlashAttribute("msgExito",
	 * "El usuario ha sido eliminado correctamente"); return "redirect:/"; }
	 */
    
    @GetMapping("/{id}/editar")
    public String mostrarFormularioDeEditarUsuario(@PathVariable Integer id, Model modelo) {
        Usuario usuario = usuarioRepositorio.findById(id).orElse(null);
        modelo.addAttribute("usuario", usuario);
        return "nuevo";
    }

    @PostMapping("/{id}/editar")
    public String actualizarContacto(@PathVariable Integer id, @Validated Usuario usuario, BindingResult bindingResult, RedirectAttributes redirect) {
        if(bindingResult.hasErrors()) {
            return "nuevo";
        }

        usuario.setId(id);
        usuarioRepositorio.save(usuario);
        
        redirect.addFlashAttribute("msgExito", "El contacto ha sido actualizado correctamente");
        return "redirect:/";
    }

    @PostMapping("/{id}/eliminar")
    public String eliminarUsuario(@PathVariable Integer id, RedirectAttributes redirect) {
        usuarioRepositorio.deleteById(id);
        redirect.addFlashAttribute("msgExito", "El contacto ha sido eliminado correctamente");
        return "redirect:/";
    }
    
    
}
