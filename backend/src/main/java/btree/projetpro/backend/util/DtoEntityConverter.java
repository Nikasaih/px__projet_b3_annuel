package btree.projetpro.backend.util;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class DtoEntityConverter {
    final ModelMapper mapper = new ModelMapper();

    public Dto entityToDto(Entities entity, Dto dto) {
        return mapper.map(entity, dto.getClass());
    }

    public List<Dto> entitiesToDtos(List<Entities> entities, Dto dto) {
        return entities.stream().map(entity -> entityToDto(entity, dto)).collect(Collectors.toList());
    }

    public Entities dtoToEntity(Dto dto, Entities ent) {
        return mapper.map(dto, ent.getClass());
    }

    public List<Entities> dtosToEntities(List<Dto> dtos, Entities ent) {
        return dtos.stream().map(dto -> dtoToEntity(dto, ent)).collect(Collectors.toList());
    }
}
