package org.example.businesslogic;

import java.util.List;

public interface IDAO<Entity, EntityID> {
    void create(Entity entity);
    List<Entity> readAll();
    Entity readById(EntityID id);
    void update(Entity entity);
    void delete(Entity entity);
}
