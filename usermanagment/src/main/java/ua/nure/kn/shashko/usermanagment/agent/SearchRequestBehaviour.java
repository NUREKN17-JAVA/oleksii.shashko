package ua.nure.kn.shashko.usermanagment.agent;

import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;

public class SearchRequestBehaviour extends Behaviour {

    private AID[] aids;
    private String firstName;
    private String lastName;

    SearchRequestBehaviour(AID[] aids, String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.aids = aids;
    }

    @Override
    public void action() {
        ACLMessage message = new ACLMessage(ACLMessage.REQUEST);
        message.setContent(firstName + "," + lastName);
        if (aids != null) {
            for (int i = 0; i < aids.length; i++) {
                message.addReceiver(aids[i]);
            }
            myAgent.send(message);
        }
    }

    @Override
    public boolean done() {
        return true;
    }
}
