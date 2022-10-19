package prr;

import java.io.Serializable;
import java.io.IOException;
import prr.exceptions.UnrecognizedEntryException;
import java.util.Map;
import java.util.TreeMap;

import prr.clients.Client;
import prr.terminals.Terminal;
import prr.terminals.BasicTerminal;
import prr.terminals.FancyTerminal;

import prr.exceptions.DuplicateClientKeyException;
import prr.exceptions.DuplicateTerminalKeyException;
import prr.exceptions.UnknownClientKeyException;
import prr.exceptions.UnknownTerminalKeyException;

import java.io.BufferedReader;
import java.io.FileReader;

import java.lang.Integer;


// FIXME add more import if needed (cannot import from pt.tecnico or prr.app)

/**
 * Class Store implements a store.
 */
public class Network implements Serializable {

	/** Serial number for serialization. */
	private static final long serialVersionUID = 202208091753L;

	/** Clients */
	private final Map<String, Client> _clients = new TreeMap<>();

	/** Terminals */
	private final Map<String, Terminal> _terminals = new TreeMap<>();

	

		// FIXME define attributes
        // FIXME define contructor(s)
        // FIXME define methods

	/**
	 * Read text input file and create corresponding domain entities.
	 * 
	 * @param filename name of the text input file
     * @throws UnrecognizedEntryException if some entry is not correct
	 * @throws IOException if there is an IO erro while processing the text file
	 */
	void importFile(String filename) throws UnrecognizedEntryException, IOException, DuplicateClientKeyException, DuplicateTerminalKeyException, UnknownClientKeyException /* FIXME maybe other exceptions */  {
		String currentLine;
		String[] splitLine;
		
		BufferedReader reader = new BufferedReader(new FileReader(filename));
		while ((currentLine = reader.readLine()) != null) {
			splitLine = currentLine.split("\\|");
			
			switch (splitLine[0]) {
				case "CLIENT" -> registerClient(splitLine[1], splitLine[2], Integer.parseInt(splitLine[3]));
				case "BASIC" -> registerBasicTerminal(splitLine[1], splitLine[2], splitLine[3]);
				case "FANCY" -> registerFancyTerminal(splitLine[1], splitLine[2], splitLine[3]);
				case "FRIENDS" -> registerFriends(splitLine);
				default -> throw new UnrecognizedEntryException(currentLine);
			}
		}
    }

	public Client registerClient(String id, String name, int taxID) throws DuplicateClientKeyException {
		if (_clients.containsKey(id)) {
			throw new DuplicateClientKeyException(id);
		}
		Client client = new Client(id, name, taxID);
		_clients.put(id, client);
		return client;
	}

	public void registerTerminal(String type, String id, String clientID, String State) throws DuplicateTerminalKeyException, UnknownClientKeyException, UnrecognizedEntryException {
		if (_terminals.containsKey(id)) {
			throw new DuplicateTerminalKeyException(id);
		}
		if (!_clients.containsKey(clientID)) {
			throw new UnknownClientKeyException(clientID);
		}
		Terminal terminal;
		switch (type) {
			case "BASIC" -> terminal = new BasicTerminal(id, _clients.get(clientID), State);
			case "FANCY" -> terminal = new FancyTerminal(id, _clients.get(clientID), State);
			default -> throw new UnrecognizedEntryException(type);
		}
	}
	
	public BasicTerminal registerBasicTerminal(String id, String clientID, String state) throws DuplicateTerminalKeyException, UnknownClientKeyException, UnrecognizedEntryException {
		if (id.length() != 6) {
			throw new UnrecognizedEntryException(id);
		}
		
		if (_terminals.containsKey(id)) {
			throw new DuplicateTerminalKeyException(id);
		}

		if(!_clients.containsKey(clientID)) {
			throw new UnknownClientKeyException(clientID);
		}

		Client client = _clients.get(clientID);
		BasicTerminal terminal = new BasicTerminal(id, client, state);
		_terminals.put(id, terminal);
		return terminal;
	}

	public FancyTerminal registerFancyTerminal(String id, String clientID, String state) throws DuplicateTerminalKeyException, UnknownClientKeyException, UnrecognizedEntryException {
		if (id.length() != 6) {
			throw new UnrecognizedEntryException(id);
		}
		
		if (_terminals.containsKey(id)) {
			throw new DuplicateTerminalKeyException(id);
		}

		if(!_clients.containsKey(clientID)) {
			throw new UnknownClientKeyException(clientID);
		}

		Client client = _clients.get(clientID);
		FancyTerminal terminal = new FancyTerminal(id, client, state);
		_terminals.put(id, terminal);
		return terminal;
	}

	public void registerFriends(String[] split) throws UnrecognizedEntryException {
		if (split.length != 3) {
			throw new UnrecognizedEntryException(split[0]);
		}

		if (!_terminals.containsKey(split[1])) {
			throw new UnrecognizedEntryException(split[1]);
		}

		Terminal friender = _terminals.get(split[1]);

		String[] toAdd = split[2].split("\\,");

		for (int i = 0; i < toAdd.length; i++) {
			if (!_terminals.containsKey(toAdd[i])) {
				throw new UnrecognizedEntryException(split[0]);
			}

			friender.addFriend(_terminals.get(toAdd[i]));
		}
	}

	public Terminal getTerminal(String id) throws UnknownTerminalKeyException {
		if (!_terminals.containsKey(id)) {
			throw new UnknownTerminalKeyException(id);
		}

		return _terminals.get(id);
	}
}



	
