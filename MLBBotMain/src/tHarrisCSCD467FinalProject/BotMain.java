package tHarrisCSCD467FinalProject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;

import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.lexmodelbuilding.AbstractAmazonLexModelBuilding;
import com.amazonaws.services.lexmodelbuilding.AmazonLexModelBuilding;
import com.amazonaws.services.lexmodelbuilding.AmazonLexModelBuildingClientBuilder;
import com.amazonaws.services.lexmodelbuilding.model.BadRequestException;
import com.amazonaws.services.lexmodelbuilding.model.BotAliasMetadata;
import com.amazonaws.services.lexmodelbuilding.model.BotMetadata;
import com.amazonaws.services.lexmodelbuilding.model.ConflictException;
import com.amazonaws.services.lexmodelbuilding.model.ContentType;
import com.amazonaws.services.lexmodelbuilding.model.DeleteBotRequest;
import com.amazonaws.services.lexmodelbuilding.model.EnumerationValue;
import com.amazonaws.services.lexmodelbuilding.model.FulfillmentActivity;
import com.amazonaws.services.lexmodelbuilding.model.FulfillmentActivityType;
import com.amazonaws.services.lexmodelbuilding.model.GetBotAliasRequest;
import com.amazonaws.services.lexmodelbuilding.model.GetBotAliasesRequest;
import com.amazonaws.services.lexmodelbuilding.model.GetBotAliasesResult;
import com.amazonaws.services.lexmodelbuilding.model.GetBotRequest;
import com.amazonaws.services.lexmodelbuilding.model.GetBotsRequest;
import com.amazonaws.services.lexmodelbuilding.model.GetBotsResult;
import com.amazonaws.services.lexmodelbuilding.model.GetIntentRequest;
import com.amazonaws.services.lexmodelbuilding.model.GetIntentsRequest;
import com.amazonaws.services.lexmodelbuilding.model.GetIntentsResult;
import com.amazonaws.services.lexmodelbuilding.model.GetSlotTypeRequest;
import com.amazonaws.services.lexmodelbuilding.model.GetSlotTypesRequest;
import com.amazonaws.services.lexmodelbuilding.model.GetSlotTypesResult;
import com.amazonaws.services.lexmodelbuilding.model.Intent;
import com.amazonaws.services.lexmodelbuilding.model.IntentMetadata;
import com.amazonaws.services.lexmodelbuilding.model.InternalFailureException;
import com.amazonaws.services.lexmodelbuilding.model.LimitExceededException;
import com.amazonaws.services.lexmodelbuilding.model.Locale;
import com.amazonaws.services.lexmodelbuilding.model.Message;
import com.amazonaws.services.lexmodelbuilding.model.NotFoundException;
import com.amazonaws.services.lexmodelbuilding.model.PreconditionFailedException;
import com.amazonaws.services.lexmodelbuilding.model.ProcessBehavior;
import com.amazonaws.services.lexmodelbuilding.model.Prompt;
import com.amazonaws.services.lexmodelbuilding.model.PutBotAliasRequest;
import com.amazonaws.services.lexmodelbuilding.model.PutBotAliasResult;
import com.amazonaws.services.lexmodelbuilding.model.PutBotRequest;
import com.amazonaws.services.lexmodelbuilding.model.PutBotResult;
import com.amazonaws.services.lexmodelbuilding.model.PutIntentRequest;
import com.amazonaws.services.lexmodelbuilding.model.PutIntentResult;
import com.amazonaws.services.lexmodelbuilding.model.PutSlotTypeRequest;
import com.amazonaws.services.lexmodelbuilding.model.PutSlotTypeResult;
import com.amazonaws.services.lexmodelbuilding.model.Slot;
import com.amazonaws.services.lexmodelbuilding.model.SlotConstraint;
import com.amazonaws.services.lexmodelbuilding.model.SlotTypeMetadata;
import com.amazonaws.services.lexmodelbuilding.model.Statement;
import com.amazonaws.services.lexruntime.AmazonLexRuntime;
import com.amazonaws.services.lexruntime.AmazonLexRuntimeClientBuilder;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.lexmodelbuilding.AmazonLexModelBuilding;
import com.amazonaws.services.lexmodelbuilding.AmazonLexModelBuildingClientBuilder;
import com.amazonaws.services.lexmodelbuilding.model.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;

public class BotMain extends AbstractAmazonLexModelBuilding  {
	
	 private static final String LATEST_VERSION = "$LATEST";



	public static void main(String[] args) {
		AmazonLexRuntime lexus = AmazonLexRuntimeClientBuilder.standard().withRegion(Regions.US_WEST_2).build();
		AmazonLexModelBuilding client = null; AmazonLexModelBuildingClientBuilder.standard().withRegion(Regions.US_WEST_2).build();
		String botName = "MLBBot", aliasName = "PlayerFinderBot", getPlayerIntentName = "GetPlayer", positionSlotTypeName = "PositionType", teamSlotTypeName = "TeamType";
		
		try {
			 client = AmazonLexModelBuildingClientBuilder.standard().withRegion(Regions.US_WEST_2).build();
			 
			 ArrayList<EnumerationValue> positionTypeValues = new ArrayList<>();

	            positionTypeValues.add(new EnumerationValue().withValue("catcher"));
	            positionTypeValues.add(new EnumerationValue().withValue("pitcher"));
	            positionTypeValues.add(new EnumerationValue().withValue("first base"));            
	            positionTypeValues.add(new EnumerationValue().withValue("second base"));
	            positionTypeValues.add(new EnumerationValue().withValue("third base"));
	            positionTypeValues.add(new EnumerationValue().withValue("left field"));	           
	            positionTypeValues.add(new EnumerationValue().withValue("center field"));
	            positionTypeValues.add(new EnumerationValue().withValue("right field"));
	            positionTypeValues.add(new EnumerationValue().withValue("shortstop"));
	            
			 createSlotType(client, "PositionType", positionTypeValues);
			 

	            ArrayList<EnumerationValue> teamTypeValues = new ArrayList<>();
	            teamTypeValues.add(new EnumerationValue().withValue("Mariners"));
	            teamTypeValues.add(new EnumerationValue().withValue("Yankees"));

	         createSlotType(client, teamSlotTypeName, teamTypeValues);

			 
			 ArrayList<String> sampleUtterances = new ArrayList<>();
			 sampleUtterances.add("Who plays {PositionType} for the {TeamType}");
			 sampleUtterances.add("I would like to find a player");
			 sampleUtterances.add("Who is number {PlayerNumber} for the {TeamType}");
			 
			 ArrayList<Slot> slots = new ArrayList<>();
			 
			 slots.add(createSlot("PositionType", positionSlotTypeName, 3,"What position?", SlotConstraint.Required));
			 slots.add(createSlot("PlayerNumber", "AMAZON.NUMBER", 3,"What's their number?", SlotConstraint.Optional));
			 slots.add(createSlot("TeamType", teamSlotTypeName, 3,"What team do they play for?", SlotConstraint.Required));

			 

			 createOrUpdateIntent(client, getPlayerIntentName, sampleUtterances, slots);
			 createBot(client, botName, Arrays.asList(getPlayerIntentName));
			 
		}
		finally {
			if(client != null)
				client.shutdown();
		}
		
	}



	private static boolean createSlotType(AmazonLexModelBuilding client, String slotTypeName, ArrayList<EnumerationValue> values) {
		
															
				try {
					String checksum = getSlotTypeChecksum(client, slotTypeName);
		        	PutSlotTypeRequest request = new PutSlotTypeRequest().withName(slotTypeName).withChecksum(checksum).withEnumerationValues(values);
		        	PutSlotTypeResult result = client.putSlotType(request);
		        	System.out.println("The slot type "+ slotTypeName + " has been created");
		        	return true;

				} catch (BadRequestException|ConflictException|InternalFailureException |LimitExceededException|PreconditionFailedException|NotFoundException e) {
						
						System.out.println("Failure during creating " + slotTypeName );
						e.printStackTrace();
				} catch (Exception e) {

						e.printStackTrace();

				} 
				
				return false;
	}



	private static String getSlotTypeChecksum(AmazonLexModelBuilding client, String slotTypeName) {

        if(isSlotTypeExist(client, slotTypeName))
            return client.getSlotType(new GetSlotTypeRequest().withName(slotTypeName).withVersion(LATEST_VERSION)).getChecksum();

        return null;

    }



    private static boolean isSlotTypeExist(AmazonLexModelBuilding client, String slotTypeName) {

        for(SlotTypeMetadata slotTypeMetadata: client.getSlotTypes(new GetSlotTypesRequest()).getSlotTypes()){
            if(slotTypeMetadata.getName().equals(slotTypeName))
            	return true;
        }

        return false;

    }



	private static boolean createOrUpdateIntent(AmazonLexModelBuilding client, String intentName, ArrayList<String> sampleUtterances, ArrayList<Slot> slots) {
		try {
        	
			String checksum = getIntentChecksum(client, intentName);
			PutIntentResult result = client.putIntent(new PutIntentRequest().withName(intentName)
																			.withChecksum(checksum)
																			.withSampleUtterances(sampleUtterances)
																			.withSlots(slots)
																			
																			.withFulfillmentActivity(new FulfillmentActivity().withType(FulfillmentActivityType.ReturnIntent)));
																			
        	System.out.println("The intent " + intentName + " has been created. Checksum: " + result.getChecksum());
        	return true;

		} catch (BadRequestException|ConflictException|InternalFailureException |LimitExceededException|PreconditionFailedException|NotFoundException e) {
				
				//System.out.println("Failure during creating " + intentName);
				e.printStackTrace();
		
		} catch (Exception e) {

				e.printStackTrace();

		} 
		return false;
	}



	private static String getIntentChecksum(AmazonLexModelBuilding client, String intentName) {
		if(isIntentExist(client, intentName)) {
			return client.getIntent(new GetIntentRequest().withName(intentName).withVersion(LATEST_VERSION)).getChecksum();
		}
		return null;
	}



	private static boolean isIntentExist(AmazonLexModelBuilding client, String intentName) {
		for(IntentMetadata intentMetadata: client.getIntents(new GetIntentsRequest()).getIntents()) {
			if(intentMetadata.getName().equals(intentName))
				return true;
		}
		return false;
	}



	private static boolean createBot(AmazonLexModelBuilding client, String botName, List<String> intentNames) {
		try {
			String checksum = getBotChecksum(client, botName);
			
			ArrayList<Intent> intents = new ArrayList<>();
			for(String intentName: intentNames)
				intents.add(new Intent().withIntentName(intentName).withIntentVersion(LATEST_VERSION));
			
			PutBotRequest request = new PutBotRequest()
					.withName(botName)
					.withChecksum(checksum)
					.withLocale(Locale.EnUS)
					.withVoiceId("Matthew")
					.withChildDirected(false)
					.withIdleSessionTTLInSeconds(60)
					.withAbortStatement(new Statement().withMessages(new Message().withContentType(ContentType.PlainText).withContent("I'm sorry, I cannot understand this.")))
					.withClarificationPrompt(new Prompt().withMaxAttempts(5).withMessages(new Message().withContentType(ContentType.PlainText).withContent("Could you pleease repeat that?")))
					.withDescription("The bot for finding your favorite player")
					.withProcessBehavior(ProcessBehavior.SAVE)
					
					.withIntents(intents);
					PutBotResult result = client.putBot(request);
					System.out.println("The bot " + botName + " has been created. CheckSum: " + result.getChecksum());
					return true;
		} 
		catch (BadRequestException|ConflictException|InternalFailureException |LimitExceededException|PreconditionFailedException|NotFoundException e) {
				
				System.out.println("Failure during creating " + botName);
				e.printStackTrace();
		} catch (Exception e) {

        e.printStackTrace();

		} 
		
		return false;		
		
		
	}
	
	private static boolean createBotAlias(AmazonLexModelBuilding client, String botName, String aliasName) {

        if(isBotAliasExist(client, botName, aliasName)) {
        	System.out.println("The alias " + aliasName + "for the bot " + botName + " already exists");
        	return false;
        }
		
		

        try {
        	PutBotAliasRequest request = new PutBotAliasRequest()
        														.withBotName(botName)
        														.withBotVersion(LATEST_VERSION)
        														.withName(aliasName)
        														.withChecksum(null);
        	PutBotAliasResult result = client.putBotAlias(request);
        	System.out.println("The alias " + aliasName + " for the bot " + botName + " has been created");
        	return true;

		} catch (BadRequestException|ConflictException|InternalFailureException |LimitExceededException|PreconditionFailedException|NotFoundException e) {
				
				System.out.println("Failure during creating " + aliasName);
		
		} catch (Exception e) {

				e.printStackTrace();

		} 
        return false;

       

    }


	private static boolean isBotAliasExist(AmazonLexModelBuilding client, String botName, String aliasName) {
			for(BotAliasMetadata botAliasMetadata: client.getBotAliases(new GetBotAliasesRequest().withBotName(botName)).getBotAliases()){
				if(botAliasMetadata.getName().equals(aliasName)) {
					return true;
				}
			
			}
									
			return false;
	}
	
	 private static boolean isBotExist(AmazonLexModelBuilding client, String botName) {

	        boolean exists = false;

	        for(BotMetadata botMetadata: client.getBots(new GetBotsRequest()).getBots()){

	            if(botMetadata.getName().equals(botName))

	                return true;

	        }

	        return false;

	}
	 
	private static String getBotChecksum(AmazonLexModelBuilding client, String botName) {

	        if (isBotExist(client, botName))

	            return client.getBot(new GetBotRequest().withName(botName).withVersionOrAlias(LATEST_VERSION)).getChecksum();



	        return null;

	}
	
	private static Slot createSlot(String slotName, String slotType, int maxAttempts, String messageContent, SlotConstraint slotConstraint) {

        Prompt valueElicitationPrompt = new Prompt().withMaxAttempts(maxAttempts).withMessages(new Message().withContentType(ContentType.PlainText).withContent(messageContent));

        Slot slot = new Slot()
        					.withName(slotName)
        					.withSlotType(slotType)
        					.withSlotConstraint(slotConstraint)
        					.withValueElicitationPrompt(valueElicitationPrompt);

        if(!slotType.startsWith("AMAZON."))
        	slot.withSlotTypeVersion(LATEST_VERSION);

        return slot;

    }
	 
	

}
