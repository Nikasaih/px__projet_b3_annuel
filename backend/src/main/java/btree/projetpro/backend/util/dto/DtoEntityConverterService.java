package btree.projetpro.backend.util.dto;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DtoEntityConverterService {
    final ModelMapper mapper = new ModelMapper();

    public Dto entityToDto(Entities entity, Dto dto) {
        return mapper.map(entity, dto.getClass());
    }

    public List<Dto> entitiesToDtos(List<Entities> entities, Dto dto) {
        return entities.stream().map(entity -> entityToDto(entity, dto)).collect(Collectors.toList());
    }

    public Entities dtoToEntity(Dto dto, Entities entity) {
        return mapper.map(dto, entity.getClass());
    }

    public List<Entities> dtosToEntities(List<Dto> dtos, Entities entity) {
        return dtos.stream().map(dto -> dtoToEntity(dto, entity)).collect(Collectors.toList());
    }
}
