package controller;
import java.io.Serializable;
import java.util.ArrayList;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import dao.TaskDAO;
import model.Task;


@ManagedBean
public class TaskController implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Task task = new Task();
	private TaskDAO taskDAO;
	private ArrayList<Task> taskList;
	private String palavraChave = "";
	
	
	
	//
	
	public String cadastrarOuAlterarTask(){
		taskDAO = new TaskDAO();
		if(this.taskDAO.incluirOuAlterarTask(getTask()) > 0){
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Task cadastrada com sucesso.",""));
			taskDAO = null;
			task = null;
		}else{
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao cadastrar task.",""));
			taskDAO = null;
			task = null;
		}
		listarTasks();
		return"index.xhtml";
	}
	
	//
	
	public void deletaraTask(){
		taskDAO = new TaskDAO();
		if(taskDAO.deletarTask(task.getIdTask())){
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Task Deletada com sucesso.",""));
			taskDAO = null;
			task = null;
		}else{
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao deletar task.",""));
			taskDAO = null;
			task = null;
		}
		listarTasks();
	}
	//
	
	public String editarTask(){
		return"editar.xhtml";
	}
	
	public String voltar(){
		return"index.xhtml";
	}
	
	//metodo acionado quando o index é construido
	@PostConstruct
	public void listarTasks(){
		taskDAO = new TaskDAO();
		taskList = taskDAO.listarTasks(getPalavraChave());
		taskDAO = null;
		setPalavraChave("");
	}
	

	public Task getTask() {
		return task;
	}

	public void setTask(Task task) {
		this.task = task;
	}

	public ArrayList<Task> getTaskList() {
		return taskList;
	}

	public void setTaskList(ArrayList<Task> taskList) {
		this.taskList = taskList;
	}

	public String getPalavraChave() {
		return palavraChave;
	}

	public void setPalavraChave(String palavraChave) {
		this.palavraChave = palavraChave;
	}

}
