package prr.communications;

import prr.terminals.Terminal;

public class TextCommunication extends Communication {

    /** Communication text */
    private String _text;

    public TextCommunication(int id, Terminal origin, Terminal destination, String text) {
        super(id, origin, destination);
        _text = text;
    }

    public String getText() {
        return _text;
    }

    public void setText(String text) {
        _text = text;
        setUnits(text.length());
    }
    
    @Override
    public long calculateCost() {
        return getOrigin().getOwner().getLevel().getTextCost(getUnits());               
    }

    @Override
    public long calculateCost(int units) {
        return getOrigin().getOwner().getLevel().getTextCost(units);               
    }

    @Override
    public String toString() {
        return "TEXT|" + getID() + "|" + getOrigin().getID() + "|" + getDestination().getID() + "|" + getUnits() + "|" + getCost() + "|" + "FINISHED";
    }

}