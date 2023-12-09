package boot.backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import boot.backend.repository.TaskEntity;
import boot.backend.repository.TaskEntity.State;
import boot.backend.repository.TaskRepository;

@Transactional
public class TaskController {

    @Autowired
    private TaskRepository taskRepository;

    public List<TaskEntity> findAllTasks() {
        // TODO Aufgabe 3: Liste aller Tasks zurückgeben
        return null;
    }

    public List<TaskEntity> findTasks(State state) {
        // TODO Aufgabe 3: Liste der passenden Tasks zurückgeben
        return null;
    }

    public TaskEntity findTask(long id) {
        // TODO Aufgabe 3: Ein Task laden

        // TODO Aufgabe 4: Fehler 404, wenn es dieses Task nicht gibt
        return null;
    }

    public void createTask(TaskEntity task) {
        // TODO Aufgabe 3: Ein neues Task anlegen

        // TODO Aufgabe 4: Fehler 409, falls id != null
        // TODO Aufgabe 4: "Location" Header mit Link zum erzeugten Task setzen
        // TODO Aufgabe 4: Status 201 bei Erfolg
    }

    public void updateTask(long id, TaskEntity task) {
        // TODO Aufgabe 3: Task ändern

        // TODO Aufgabe 4: Fehler 404, wenn es dieses Task nicht gibt
        // TODO Aufgabe 4: Fehler 409, falls id != task.id
    }

    public void deleteTask(long id) {
        // TODO Aufgabe 3: Task löschen, falls vorhanden

        // TODO Aufgabe 4: Fehler 404, wenn es dieses Task nicht gibt
        // TODO Aufgabe 4: Status 204 bei Erfolg
    }
}