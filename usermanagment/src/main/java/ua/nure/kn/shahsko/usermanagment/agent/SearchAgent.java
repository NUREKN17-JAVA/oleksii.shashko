package ua.nure.kn.shahsko.usermanagment.agent;

import jade.core.Agent;

public class SearchAgent extends Agent {
    protected void setup() {
        super.setup();
        System.out.println(getAID().getName() + " started");
    }

    protected void takeDown() {
        System.out.println(getAID().getName() + " terminated");
        super.takeDown();
    }
}
