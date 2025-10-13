package com.bedrye.bjge.GameEngine.Util.Interfaces;

import com.bedrye.bjge.GameEngine.Objects.Object3DAbstract;
import com.bedrye.bjge.GameEngine.Objects.Scene;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import java.util.List;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.CLASS, // stores full class name for polymorphic types
        include = JsonTypeInfo.As.PROPERTY,
        property = "@class"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = Scene.class),
        @JsonSubTypes.Type(value = Object3DAbstract.class)
})
@JsonIdentityInfo(
        generator = ObjectIdGenerators.UUIDGenerator.class,
        property = "@id"
)
public interface IGameSpace {
    void addChildObject(Object3DAbstract object3DAbstract);
    void removeChildObject(Object3DAbstract object3DAbstract);
    boolean hasChildren();
    List<Object3DAbstract> getChildList();

    boolean isFinal();
}
