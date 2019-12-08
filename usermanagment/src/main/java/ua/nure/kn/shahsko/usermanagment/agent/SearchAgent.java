package ua.nure.kn.shahsko.usermanagment.agent;

import jade.core.AID;
import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import ua.nure.kn.shahsko.usermanagment.db.DaoFactory;
import ua.nure.kn.shahsko.usermanagment.db.DatabaseException;
import ua.nure.kn.shahsko.usermanagment.domain.User;

import java.util.Collection;

public class SearchAgent extends Agent {
    protected void setup() {
        super.setup();
        System.out.println(getAID().getName() + " started");

        DFAgentDescription description = new DFAgentDescription();
        description.setName(getAID());
        ServiceDescription serviceDescription = new ServiceDescription();
        serviceDescription.setName("JADE-searching");
        serviceDescription.setType("searching");
        description.addServices(serviceDescription);

        try {
            DFService.register(this, description);
        } catch (FIPAException e) {
            e.printStackTrace();
        }

        addBehaviour(new RequestServer());
    }

    protected void takeDown() {
        System.out.println(getAID().getName() + " terminated");

        try {
            DFService.deregister(this);
        } catch (FIPAException e) {
            e.printStackTrace();
        }

        super.takeDown();
    }

    public void search(String firstName, String lastName) throws SearchException {
        try {
            Collection<User> users = DaoFactory.getInstance().getUserDao().find(firstName, lastName);
            if (users.size() > 0) {
                showUsers(users);
            } else {
                addBehaviour(new SearchRequestBehaviour(new AID[] {}, firstName, lastName));
            }
        } catch (DatabaseException | ReflectiveOperationException e) {
            throw new SearchException(e);
        }
    }

    void showUsers(Collection<User> users) {

    }
}
