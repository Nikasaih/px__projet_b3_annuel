package btree.projetpro.backend.lib.dataobject;

import btree.projetpro.backend.lib.dataobject.dto.Dto;
import btree.projetpro.backend.lib.dataobject.entity.AbstractEntity;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DataObjectMatcher<T extends AbstractEntity> {
    final ModelMapper mapper = new ModelMapper();

    public Dto entityToDto(AbstractEntity entity, Dto dto) {
        return mapper.map(entity, dto.getClass());
    }

    public List<Dto> entitiesToDtos(List<AbstractEntity> entities, Dto dto) {
        return entities.stream().map(entity -> entityToDto(entity, dto)).collect(Collectors.toList());
    }

    public T dtoToEntity(Dto dto, T entity) {
        return (T) mapper.map(dto, entity.getClass());
    }


    public List<AbstractEntity> dtosToEntities(List<Dto> dtos, T entity) {
        return dtos.stream().map(dto -> dtoToEntity(dto, entity)).collect(Collectors.toList());
    }
}
