package prr.terminals;

import prr.clients.Client;

import java.util.Map;
import java.util.TreeMap;

public class FancyTerminal extends BasicTerminal {

    public FancyTerminal(String id, Client owner){
        super(id, owner);
    }

    public FancyTerminal(String id, Client owner, String state) {
        super(id, owner, state);
    }

    public void startVideoCommunication(int receiverID){
        // TODO
    }

    @Override
    public boolean isFancy(){
        return true;
    }

    @Override
    public String toString() {

        Map<String, Terminal> friends = getFriends();

        StringBuilder sb = new StringBuilder();

        if (friends.size() > 0) {
            sb.append("FANCY|" + getID() + "|" + getOwner().getID() + "|" + "state" + "|" + Math.round(getPayments()) + "|" + Math.round(getDebt()) + "|");

            for (Terminal friend : friends.values()) {
                sb.append(friend.getID() + ",");
            }

            sb.deleteCharAt(sb.length() - 1);
		    return sb.toString();
        }
        else {
            return "FANCY|" + getID() + "|" + getOwner().getID() + "|" + getState().toString() + "|" + Math.round(getPayments()) + "|" + Math.round(getDebt());
        }
    }
}