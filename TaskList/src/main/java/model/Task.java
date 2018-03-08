package model;

import java.util.Date;

public class Task {
	
	private int idTask;
	private String tituloTask;
	private String descricaoTask;
	private int statusTask;
	private Date dataCriacao;
	
	
	public int getIdTask() {
		return idTask;
	}
	public void setIdTask(int idTask) {
		this.idTask = idTask;
	}
	public String getTituloTask() {
		return tituloTask;
	}
	public void setTituloTask(String tituloTask) {
		this.tituloTask = tituloTask;
	}
	public String getDescricaoTask() {
		return descricaoTask;
	}
	public void setDescricaoTask(String descricaoTask) {
		this.descricaoTask = descricaoTask;
	}
	public int getStatusTask() {
		return statusTask;
	}
	public void setStatusTask(int statusTask) {
		this.statusTask = statusTask;
	}
	public Date getDataCriacao() {
		return dataCriacao;
	}
	public void setDataCriacao(Date dataCriacao) {
		this.dataCriacao = dataCriacao;
	}
	
	
	
}
