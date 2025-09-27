package com.emobile.springtodo.mapper;

import com.emobile.springtodo.DTO.TodoDTO;
import com.emobile.springtodo.DTO.TodoRequestDTO;
import com.emobile.springtodo.entity.Todo;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TodoMapper {

    // Entity -> DTO
    public TodoDTO toDTO(Todo todo) {
        if (todo == null) return null;
        return new TodoDTO(
                todo.getId(),
                todo.getTitle(),
                todo.isCompleted()
        );
    }

    // List<Entity> -> List<DTO>
    public List<TodoDTO> toDTOs(List<Todo> todos) {
        if (todos == null) return List.of();
        return todos.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    // DTO -> Entity
    public Todo toEntity(TodoRequestDTO dto) {
        if (dto == null) return null;
        return new Todo(
                dto.getTitle(),
                dto.isCompleted()
        );
    }

    // Обновление существующей Entity из DTO
    public void updateEntityFromDTO(TodoRequestDTO dto, Todo entity) {
        if (dto == null || entity == null) return;
        entity.setTitle(dto.getTitle());
        entity.setCompleted(dto.isCompleted());
    }
}
