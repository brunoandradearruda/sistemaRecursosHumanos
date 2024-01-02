package com.mballem.curso.boot.dao;


import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.mballem.curso.boot.domain.Funcionario;

@Repository
public class FuncionarioDaoImpl extends AbstractDao<Funcionario, Long> implements FuncionarioDao {

	public List<Funcionario> findByNome(String nome) {
		return createQuery("select f from Funcionario f where f.nome like concat('%', ?1, '%')", nome);
	}

	@Override
	public List<Funcionario> findByCargo(long id) {
		return createQuery("select f from Funcionario f where f.cargo.id = ?1", id);
	}

	@Override
	public List<Funcionario> findByData(LocalDate entrada, LocalDate saida) {
		String jpql = "select f from Funcionario f where f.dataEntrada >= ?1 and f.dataSaida <= ?2 order by f.dataEntrada asc";
		return createQuery(jpql, entrada, saida);
	}

	@Override
	public List<Funcionario> findByDataEntrada(LocalDate entrada) {
		String jpql = "select f from Funcionario f where f.dataEntrada >= ?1 order by f.dataEntrada asc";
		return createQuery(jpql, entrada);
	}

	@Override
	public List<Funcionario> findByDataSaida(LocalDate saida) {
		String jpql = "select f from Funcionario f where f.dataSaida <= ?1 order by f.dataEntrada asc";
		return createQuery(jpql, saida);
	}

	@Override
	public List<Funcionario> findByMatricula(String matricula) {
		return createQuery("select f from Funcionario f where f.matricula = ?1", matricula);
	}
}