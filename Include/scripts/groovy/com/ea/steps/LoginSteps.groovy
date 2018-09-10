package com.ea.steps
import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.checkpoint.Checkpoint
import com.kms.katalon.core.checkpoint.CheckpointFactory
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testcase.TestCase
import com.kms.katalon.core.testcase.TestCaseFactory
import com.kms.katalon.core.testdata.TestData
import com.kms.katalon.core.testdata.TestDataFactory
import com.kms.katalon.core.testobject.ObjectRepository
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords

import internal.GlobalVariable
import io.cucumber.datatable.DataTable
import MobileBuiltInKeywords as Mobile
import WSBuiltInKeywords as WS
import WebUiBuiltInKeywords as WebUI

import org.openqa.selenium.WebElement
import org.openqa.selenium.WebDriver
import org.openqa.selenium.By

import com.kms.katalon.core.mobile.keyword.internal.MobileDriverFactory
import com.kms.katalon.core.webui.driver.DriverFactory

import com.kms.katalon.core.testobject.RequestObject
import com.kms.katalon.core.testobject.ResponseObject
import com.kms.katalon.core.testobject.ConditionType
import com.kms.katalon.core.testobject.TestObjectProperty

import com.kms.katalon.core.mobile.helper.MobileElementCommonHelper
import com.kms.katalon.core.util.KeywordUtil

import com.kms.katalon.core.webui.exception.WebElementNotFoundException

import cucumber.api.java.en.And
import cucumber.api.java.en.Given
import cucumber.api.java.en.Then
import cucumber.api.java.en.When


import cucumber.api.TypeRegistry;
import cucumber.api.TypeRegistryConfigurer;
import io.cucumber.cucumberexpressions.ParameterType;
import io.cucumber.datatable.DataTable;
import io.cucumber.datatable.DataTableType;
import io.cucumber.datatable.TableCellTransformer;
import io.cucumber.datatable.TableEntryTransformer;
import io.cucumber.datatable.TableRowTransformer;
import io.cucumber.datatable.TableTransformer;


/*
 * Author: Karthik KK
 * Company: ExecuteAutomation
 * Type: StepDefinition
 * Step : LoginStep
 */
class LoginSteps {
	@Given("I navigate to the login page")
	def I_navigate_to_the_login_page() {
		WebUI.openBrowser('')
		WebUI.navigateToUrl('http://www.executeautomation.com/demosite/Login.html')
	}

	
	//Obsolete step
	@When('I enter the username as "(.*) and password as "(.*)"')
	def I_enter_username_password(String userName, String password) {
		WebUI.setText(findTestObject('Object Repository/Page_Execute Automation/input_Login_UserName'), userName)

		WebUI.setText(findTestObject('Object Repository/Page_Execute Automation/input_Login_Password'), password)
	}
	
	@And("I enter the following for Login")
	def I_enter_the_following_for_login(List<User> table){
//		Way 1 - To get data from DataTable Type
//		List<Map<String, String>> data = table.asMaps(String.class, String.class);
		
//		Way 2 - To get work with custom types using Lust
//		//Create an ArrayList
//		List<User> users =  new ArrayList<User>();
//		//Store all the users
//		users = table.asList(User.class);
		
		
		//Iterate through the values
		for (User user: table){
			WebUI.setText(findTestObject('Object Repository/Page_Execute Automation/input_Login_UserName'), user.username)
			
			WebUI.setText(findTestObject('Object Repository/Page_Execute Automation/input_Login_Password'), user.password)
		}
	}
	

	@Then("I click the login button")
	def I_Click_login_button() {
		WebUI.click(findTestObject('Object Repository/Page_Execute Automation/input_Login_Login'))
	}

	@Then("I should see the home page")
	def I_Should_see_the_home_page(){

		//Assertions has been done !
	}
}


//Custom class responsible to get UserName and Password from table steps
class User {
	public String username;
	public String password;

	public User(String userName, String passWord) {
		username= userName;
		password = passWord;
	}
}
  
 
 //Custom Transformer to convert the custom User type
 class Configurer implements TypeRegistryConfigurer {
	 
		 @Override
		 public void configureTypeRegistry(TypeRegistry registry) {
	 
			 registry.defineDataTableType(new DataTableType(User.class, new TableEntryTransformer<User>() {
						 @Override
						 public User transform(Map<String, String> entry) {
							 return new User(entry.get("username"),entry.get("password"));
						 }
					 }));
		 }
	 
		 @Override
		 public Locale locale() {
			 return Locale.ENGLISH;
		 }
	 
}
