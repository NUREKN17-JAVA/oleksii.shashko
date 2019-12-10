package ua.nure.kn.shashko.usermanagment.agent;

import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import ua.nure.kn.shashko.usermanagment.db.DaoFactory;
import ua.nure.kn.shashko.usermanagment.db.DatabaseException;
import ua.nure.kn.shashko.usermanagment.domain.User;

import java.util.*;

public class RequestServer extends CyclicBehaviour {
    @Override
    public void action() {
        ACLMessage message = myAgent.receive();
        if (message != null) {
            if (message.getPerformative() == ACLMessage.REQUEST) {
               myAgent.send(createReply(message));
            } else {
                Collection<User> users = parseMessage(message);
                ((SearchAgent) myAgent).showUsers(users);
            }
        } else {
            block();
        }
    }

    private Collection<User> parseMessage(ACLMessage message) {
        Collection<User> users = new LinkedList<User>();

        String content = message.getContent();
        if (content != null) {
            StringTokenizer tokenizer = new StringTokenizer(content, ";");
            while (tokenizer.hasMoreTokens()) {
                String userInfo = tokenizer.nextToken();
                StringTokenizer tokenizer1 = new StringTokenizer(userInfo, ",");

                String id = tokenizer1.nextToken();
                String firstName = tokenizer1.nextToken();
                String lastName = tokenizer1.nextToken();
                users.add(new User(Long.valueOf(id), firstName, lastName, null));
            }
        }

        return users;
    }

    private ACLMessage createReply(ACLMessage message) {
        ACLMessage reply = message.createReply();

        String content = message.getContent();
        StringTokenizer tokenizer = new StringTokenizer(content, ",");
        if (tokenizer.countTokens() == 2) {
            String firstName = tokenizer.nextToken();
            String lastName = tokenizer.nextToken();

            Collection<User> users = null;
            try {
                users = DaoFactory.getInstance().getUserDao().find(firstName, lastName);
            } catch (DatabaseException | ReflectiveOperationException e) {
                e.printStackTrace();
                users = new ArrayList<>(0);
            }

            StringBuffer buffer = new StringBuffer();
            for (Iterator it = users.iterator(); it.hasNext();) {
                User user = (User) it.next();
                buffer.append(user.getId()).append(",");
                buffer.append(user.getFirstName()).append(",");
                buffer.append(user.getLastName()).append(";");
            }

            reply.setContent(buffer.toString());
        }
        return reply;
    }
}
