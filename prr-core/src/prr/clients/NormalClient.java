package prr.clients;

public class NormalClient extends ClientLevel {
    public NormalClient(Client client){
        super(client);
    }

    @Override
    public void normal() {
        // Client is already normal
    }

    @Override
    public void gold() {
        if (_client.getBalance() > 500) {
            _client.setLevel(new GoldClient(_client));
        }
    }

    @Override
    public void platinum() {
        // Not possible
    }

    public long getTextCost(int length) {
        return _client.getTariff().getTextCostNormal(length);
    }

    public long getVoiceCost(int duration) {
        return _client.getTariff().getVoiceCostNormal(duration);
    }

    public long getVideoCost(int duration) {
        return _client.getTariff().getVideoCostNormal(duration);
    }

    @Override
    public String toString() {
        return "NORMAL";
    }
}