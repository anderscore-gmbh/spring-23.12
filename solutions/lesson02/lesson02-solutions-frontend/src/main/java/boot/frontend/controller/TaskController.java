package boot.frontend.controller;

import static boot.frontend.model.Task.State.DONE;
import static boot.frontend.model.Task.State.STARTED;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;


import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import boot.frontend.model.Task;
import boot.frontend.model.Task.State;
import boot.frontend.service.TaskService;

@Controller
public class TaskController {
	private static final Logger log = LoggerFactory.getLogger(TaskController.class);
	private static final String VIEWS_CREATE_OR_UPDATE_FORM = "tasks/createOrUpdateForm";

	private final TaskService taskService;

	@Autowired
	public TaskController(TaskService taskService) {
		this.taskService = taskService;
	}

	@InitBinder
	public void setAllowedFields(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}

	@ModelAttribute("states")
	public List<State> populateFeatures() {
		return Arrays.asList(State.values());
	}

	@GetMapping("/tasks")
	public String list(Map<String, Object> model) {
		Collection<Task> results = taskService.findAll();
		model.put("tasks", results);
		return "tasks/taskList";
	}

	@GetMapping("/tasks/new")
	public ModelAndView initCreationForm() {
		var mav = new ModelAndView(VIEWS_CREATE_OR_UPDATE_FORM);
		mav.addObject(new Task()); // verwendet key 'task'
		return mav;
	}

	@PostMapping("/tasks/new")
	public String processCreationForm(@Valid Task task, BindingResult result) {
		if (result.hasErrors()) {
			return VIEWS_CREATE_OR_UPDATE_FORM;
		} else {
			taskService.create(task);
			return "redirect:/tasks";
		}
	}

	@GetMapping("/tasks/{taskId}/edit")
	public String initUpdateForm(@PathVariable("taskId") long taskId, Model model) {
		Task task = taskService.findById(taskId);
		model.addAttribute(task);
		return VIEWS_CREATE_OR_UPDATE_FORM;
	}

	@PostMapping("/tasks/{taskId}/edit")
	public String processUpdateForm(@Valid Task task, BindingResult result, @PathVariable("taskId") long taskId) {
		if (result.hasErrors()) {
			return VIEWS_CREATE_OR_UPDATE_FORM;
		} else {
			task.setId(taskId);
			taskService.update(task);
			return "redirect:/tasks";
		}
	}

	@GetMapping("/tasks/{taskId}/delete")
	public String deleteTask(@PathVariable("taskId") long taskId) {
		taskService.delete(taskId);
		return "redirect:/tasks";
	}

	@GetMapping("/tasks/{taskId}/updateStatus")
	public String updateTaskStatus(@PathVariable("taskId") long taskId) {
		Task task = taskService.findById(taskId);
		State state = task.getState();

		switch (state) {
		case OPEN:
			task.setState(STARTED);
			break;
		default:
			task.setState(DONE);
			break;
		}

		taskService.update(task);

		return "redirect:/tasks";
	}
}