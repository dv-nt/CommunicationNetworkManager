package prr.app.clients;

import prr.Network;

import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

import prr.app.exceptions.UnknownClientKeyException;
//FIXME add more imports if needed

/**
 * Register new client.
 */
class DoRegisterClient extends Command<Network> {

	DoRegisterClient(Network receiver) {
		super(Label.REGISTER_CLIENT, receiver);
                //FIXME add command fields
				addStringField("id", Prompt.key());
				addStringField("name", Prompt.name());
				addIntegerField("taxID", Prompt.taxId());
	}

	@Override
	protected final void execute() throws CommandException {
			//try {
				_receiver.registerClient(
					stringField("id"),
					stringField("name"),
					integerField("taxID")
				); 
			//} catch (DuplicateClientKeyException e) {
			//	throw new DuplicateClientKeyException(stringField("id"));
			//}
	}

}
