package ua.prom.roboticsdmc.mapper;

public interface Mapper<E, D> {
    
    E mapDomainToEntity(D item);

    D mapEntityToDomain(E entity);
    
}
