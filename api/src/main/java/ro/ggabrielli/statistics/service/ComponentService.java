package ro.ggabrielli.statistics.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ro.ggabrielli.statistics.domain.component.Group;
import ro.ggabrielli.statistics.repository.jpa.component.ComponentRepository;

@Component
public class ComponentService {

    private final ComponentRepository componentRepository;

    public ComponentService(@Autowired ComponentRepository componentRepository){
           this.componentRepository = componentRepository;
       }

       public void addComponent(Group component){

       }

       public void removeComponent(Group group){

       }

       public void updateCompoent(Group group){

       }
}
