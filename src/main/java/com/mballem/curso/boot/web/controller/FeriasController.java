package com.mballem.curso.boot.web.controller;

import com.mballem.curso.boot.domain.Cargo;
import com.mballem.curso.boot.domain.Departamento;
import com.mballem.curso.boot.service.CargoService;
import com.mballem.curso.boot.service.DepartamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/ferias")
public class FeriasController {
	
	@Autowired
	private CargoService cargoService;
	@Autowired
	private DepartamentoService departamentoService; //para fazer a consulta pela lista de departamentos
	
	@GetMapping("/cadastrar")
	public String cadastrar(Cargo cargo) {
		return "cargo/cadastro";
	}
	
	@GetMapping("/listar")
	public String listar(ModelMap model) {
		model.addAttribute("cargos", cargoService.buscarTodos());
		return "cargo/lista";
	}
	
	//salva os dados do cargo, passa valor para um attr via apelido
	@PostMapping("/salvar")
	public String salvar(@Valid Cargo cargo, BindingResult result, RedirectAttributes attr) {
		
		if(result.hasErrors()) {
			return "cargo/cadastro";
		}
		
		cargoService.salvar(cargo);
		attr.addFlashAttribute("success", "Cargo inserido com sucesso");
		return "redirect:/cargos/cadastrar";
	}
	
	//Aqui ele vai listar os departamentos, devido a anotação, ja vai direto para la
	@ModelAttribute("departamentos") //nome da variável é departamentos
	public List<Departamento> listaDeDepartamentos(){
		return departamentoService.buscarTodos(); //retorna uma lista de departamentos
	}
	
	@GetMapping("/editar/{id}")
	public String preEditar(@PathVariable("id") Long id,  ModelMap model) {
		model.addAttribute("cargo", cargoService.buscarPorId(id));
		return "cargo/cadastro";
	}
	
	@PostMapping("/editar")
	public String editar(Cargo cargo, RedirectAttributes attr){
		cargoService.editar(cargo);
		attr.addFlashAttribute("success", "Cargo atualizado com sucesso");
		return "redirect:/cargos/cadastrar";
	}
	
	@GetMapping("/excluir/{id}")
	public String excluir(@PathVariable("id") Long id, RedirectAttributes attr) {
		if(cargoService.cargoTemFuncionario(id)){
			attr.addFlashAttribute("fail", "Cargo não excluido. Tem funcionário(s) vinculado(s).");
		}else {
			attr.addFlashAttribute("success", "Cargo excluído com sucesso!");
		}
		return "redirect:/cargos/listar";
	}
	
	
	
	
	
}                                                             
