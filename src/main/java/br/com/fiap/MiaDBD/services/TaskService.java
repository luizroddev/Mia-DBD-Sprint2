package br.com.fiap.MiaDBD.services;

import br.com.fiap.MiaDBD.models.Task;
import br.com.fiap.MiaDBD.repositories.TaskRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public Task getTaskById(Integer id) {
        return taskRepository.findById(id).orElse(null);
    }

    public void createTask(Task task) {
        task.setCreatedAt(LocalDateTime.now());
        taskRepository.save(task);
    }

    public void updateTask(Integer id, Task task) {
        Task existingTask = taskRepository.findById(id).orElse(null);

        if (existingTask != null) {
            task.setId(id);
            task.setCreatedAt(existingTask.getCreatedAt());
            taskRepository.save(task);
        }
    }

    public void deleteTask(Integer id) {
        taskRepository.findById(id).ifPresent(taskRepository::delete);
    }
}
