package com.cordys.uiunit.eastwind.mdm;

import org.junit.Test;

import com.cordys.cm.uiunit.elements.html.IHTMLElement;
import com.cordys.cm.uiunit.elements.html.ITextInput;
import com.cordys.cm.uiunit.junit4.Assert;
import com.cordys.cm.uiunit.junit4.UIUnitTestCase;
import com.cordys.cm.uiunit.junit4.annotation.UIUnit;
import com.cordys.cm.uiunit.junit4.annotation.UIUnitTimeout;
import com.cordys.cusp.util.CordysRuntimeApplications;
import com.cordys.cusp.util.ICUSPApplication;

@UIUnit(startUrl="http://<default>/cordys/com/cordys/cusp/cusp.caf")
public class ModifyProductName extends UIUnitTestCase{	
	
	@Test
	@UIUnitTimeout(1200000)
	public void modifyProductName(){		
		ICUSPApplication ordersApplication = CordysRuntimeApplications.startFromCUSP(this.getContext(), IManageProduct.class);
		ordersApplication.maximize();			
		ordersApplication.findElement("button1").click();
		this.getContext().waitForIdle();
		IHTMLElement ordersGroup = ordersApplication.findElement("ProductsTable");
		ITextInput field = ordersGroup.findElement(ITextInput.class,"productid");
		Assert.assertEquals("77",field.getValue());
		field = ordersGroup.findElement(ITextInput.class,"productname");
		Assert.assertEquals("Coffee",field.getValue());
		field.setValue("Juice");
		IHTMLElement saveBtn = ordersApplication.findElement("toolbarSaveButton");
		saveBtn.click();
		this.getContext().waitForIdle();		
	}
}
