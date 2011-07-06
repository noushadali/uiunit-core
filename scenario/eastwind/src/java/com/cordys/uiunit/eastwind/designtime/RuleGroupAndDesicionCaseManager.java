package com.cordys.uiunit.eastwind.designtime;

import org.junit.Test;

import com.cordys.cm.uiunit.junit4.annotation.UIUnitTimeout;
import com.cordys.cws.uiunit.util.folder.IUICWSFolder;
import com.cordys.decisioncase.cwsutilities.IUIDecisionCase;
import com.cordys.decisioncase.cwsutilities.IUIDecisionCaseEditor;
import com.cordys.decisioncase.cwsutilities.valuetypes.Condition;
import com.cordys.decisioncase.cwsutilities.valuetypes.IMinMaxPair;
import com.cordys.decisioncase.cwsutilities.valuetypes.ISingleValue;
import com.cordys.rule.cwsutilities.actions.Action;
import com.cordys.rule.cwsutilities.actions.IAssignmentActionDC;
import com.cordys.rulegroup.cwsutilities.IUIRuleGroup;
import com.cordys.rulegroup.cwsutilities.IUIRuleGroupEditor;
import com.cordys.webservice.cwsutilities.DecisionCaseWebServiceInterfaceProperties;
import com.cordys.webservice.cwsutilities.IUIWebServiceDefinitionSet;
import com.cordys.webservice.cwsutilities.IUIWebServiceDefinitionSetCreatorWizard;
import com.cordys.webservice.cwsutilities.SourceType;
import com.cordys.xmlschema.cwsutilities.IUISchemaFragment;
import com.cordys.xmlschema.cwsutilities.IUIXMLSchemaDefinition;

public class RuleGroupAndDesicionCaseManager extends WorkSpaceOrganizer
{	
	@Test
	@UIUnitTimeout(800000)
	public void createRuleGroup()
	{
		//Find the Rules Folder
		IUICWSFolder folder = getProject().getExistingChildDocument(IUICWSFolder.class, EastWindArtifacts.FOLDER_RULES);
		//Create a Rule Group
		IUIRuleGroup ruleGroup = folder.addDocument(IUIRuleGroup.class);
		//Open the RuleGroup editor
		IUIRuleGroupEditor ruleGroupEditor = ruleGroup.openDesigner();
		//Save the RuleGroup
		ruleGroupEditor.save(EastWindArtifacts.RULE_GROUP_ORDERS, EastWindArtifacts.RULE_GROUP_ORDERS);
		//Close the RuleGroup Editor
		ruleGroupEditor.close();
	}
	@Test
	@UIUnitTimeout(800000)
	public void createDesicionCase() throws InterruptedException
	{
		//Find the Rule Group to add the decision case
		IUIRuleGroup ruleGroup = getProject().getExistingChildDocument(IUICWSFolder.class, EastWindArtifacts.FOLDER_RULES).getExistingChildDocument(IUIRuleGroup.class, EastWindArtifacts.RULE_GROUP_ORDERS);
		//Find the schema fragment to Create decision case 
		IUISchemaFragment schemaFragment = getProject().getExistingChildDocument(IUICWSFolder.class,EastWindArtifacts.FOLDER_CUSTOMSCHEMAS).getExistingChildDocument(IUIXMLSchemaDefinition.class, EastWindArtifacts.SCHEMA_DEF_ORDERDETAILS).getExistingChildDocument(IUICWSFolder.class, "Elements").getExistingChildDocument(IUISchemaFragment.class, EastWindArtifacts.SCHEMA_FRAG_ORDERDETAILS);
		//Create the decision case 
		IUIDecisionCase decisionCase = ruleGroup.addDecisionCaseDocument();
		IUIDecisionCaseEditor decisionCaseEditor = decisionCase.openDesigner();
		//Set the schema fragment
		decisionCaseEditor.setSchema(getProject().getName()+"/"+EastWindArtifacts.FOLDER_CUSTOMSCHEMAS+"/"+EastWindArtifacts.SCHEMA_DEF_ORDERDETAILS+"/"+EastWindArtifacts.SCHEMA_FRAG_ORDERDETAILS);
		//Create Min-Max Condition
		IMinMaxPair MinMaxCondition = Condition.getMinMaxPairCondition();
		//Add Condition
		MinMaxCondition.setMaxValue("50");
		MinMaxCondition.setMinValue("25");
		decisionCaseEditor.addCondition("sch:Quantity", MinMaxCondition, 1);
		//Add Condition
		MinMaxCondition.setMaxValue("100");
		MinMaxCondition.setMinValue("50");
		decisionCaseEditor.addCondition("sch:Quantity", MinMaxCondition, 2);
		//Create Single value Condition
		ISingleValue SingleValueCondition = Condition.getSigleValueCondition();
		//Add Condition
		SingleValueCondition.setValue("1");
		decisionCaseEditor.addCondition("sch:ProductID", SingleValueCondition, 1);
		//Add Condition
		SingleValueCondition.setValue("2");
		decisionCaseEditor.addCondition("sch:ProductID", SingleValueCondition, 2);
		//Add Condition
		SingleValueCondition.setValue("14.40");
		decisionCaseEditor.addCondition("sch:UnitPrice", SingleValueCondition, 1);
		//Add Condition
		SingleValueCondition.setValue("15");
		decisionCaseEditor.addCondition("sch:UnitPrice", SingleValueCondition, 2);
	
		//Create Assignment Action
		IAssignmentActionDC assignmentAction = Action.getAssignmentActionDC("Set DiscountRange");
		
		//Add Action
		assignmentAction.setAttribute("sch:DiscountRange");
		assignmentAction.setName("'0.20 to 0.50'");
		assignmentAction.addAssignmentValue("'0.20 to 0.50'");
		decisionCaseEditor.addAction(assignmentAction, 1);
		
		//Add Action
		assignmentAction.setAttribute("sch:DiscountRange");
		assignmentAction.setName("'0.10 to 0.30'");
		assignmentAction.addAssignmentValue("'0.10 to 0.30'");
		decisionCaseEditor.addAction(assignmentAction, 2);
		
		//Save the Decision case
		decisionCaseEditor.save(EastWindArtifacts.DECISION_CASE, EastWindArtifacts.DECISION_CASE);
		//close the Decision Case
		decisionCaseEditor.close();
	}
	@Test
	@UIUnitTimeout(800000)
	public void createWebServiceOnDecisionCase()
	{
		 IUICWSFolder folder = getProject().getExistingChildDocument(IUICWSFolder.class, EastWindArtifacts.FOLDER_WEBSERVICEDECISIONCASE);
		
		
		 IUIWebServiceDefinitionSetCreatorWizard wsdefsetcreationwizz = (IUIWebServiceDefinitionSetCreatorWizard)folder.addDocumentWithWizard(IUIWebServiceDefinitionSet.class);
		 wsdefsetcreationwizz.setSourceType(SourceType.DECISION_CASE);
		 wsdefsetcreationwizz.setName(EastWindArtifacts.WS_DEFINITIONSET_DECISIONCASE);
		 
		 wsdefsetcreationwizz.setNameSpace("http://schemas.cordys.com/"+EastWindArtifacts.WS_DEFINITIONSET_DECISIONCASE);
		  
		 DecisionCaseWebServiceInterfaceProperties decisionwsiProperties = new DecisionCaseWebServiceInterfaceProperties();
		 decisionwsiProperties.setDocumentName(EastWindArtifacts.DECISION_CASE);
		 decisionwsiProperties.setWebServiceInterfaceName(EastWindArtifacts.WS_INTERFACE_DECISIONCASE);
		 decisionwsiProperties.setWebServiceOperationName(EastWindArtifacts.WS_OPERATION_DECISIONCASE);		 
		  
		 wsdefsetcreationwizz.createWebServiceInterface(decisionwsiProperties);
		 wsdefsetcreationwizz.close();
	}
}
