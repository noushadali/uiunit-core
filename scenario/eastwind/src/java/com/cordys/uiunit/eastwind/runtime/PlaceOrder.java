package com.cordys.uiunit.eastwind.runtime;

import org.junit.Test;

import com.cordys.cm.uiunit.elements.cordys.IConfirm;
import com.cordys.cm.uiunit.elements.cordys.internal.Grid;
import com.cordys.cm.uiunit.elements.html.IButton;
import com.cordys.cm.uiunit.elements.html.IDropDown;
import com.cordys.cm.uiunit.elements.html.IHTMLElement;
import com.cordys.cm.uiunit.elements.html.IRow;
import com.cordys.cm.uiunit.elements.html.ITextInput;
import com.cordys.cm.uiunit.junit4.UIUnitTestCase;
import com.cordys.cm.uiunit.junit4.annotation.UIUnit;
import com.cordys.cm.uiunit.junit4.annotation.UIUnitTimeout;
import com.cordys.cusp.util.CordysRuntimeApplications;
import com.cordys.cusp.util.ICUSPApplication;

@UIUnit(startUrl="http://<default>/cordys/com/cordys/cusp/cusp.caf")
public class PlaceOrder extends UIUnitTestCase{
	
	@Test
	@UIUnitTimeout(1200000)
	public void openingApplicationFromCusp(){		
		ICUSPApplication ordersApplication = CordysRuntimeApplications.startFromCUSP(this.getContext(), IOrderRegistration.class);
		ordersApplication.maximize();
		//ordersApplication.findElement("navInsert1").click();
		IHTMLElement bodyGroup = ordersApplication.findElement("xbody_group");
		IHTMLElement ordersGroup = bodyGroup.findElement("OrdersGroup");
		ITextInput customerId = ordersGroup.findElement(ITextInput.class,"customerid");
		customerId.setValue("ALFKI");
		ITextInput orderdate = ordersGroup.findElement(ITextInput.class,"orderdate");
		orderdate.setValue("10/14/2009");
		ITextInput requireddate = ordersGroup.findElement(ITextInput.class,"requireddate");
		requireddate.setValue("10/21/2009");
		ITextInput shipaddress = ordersGroup.findElement(ITextInput.class,"shipaddress");
		shipaddress.setValue("Hyderabad");
		ITextInput shipcity = ordersGroup.findElement(ITextInput.class,"shipcity");
		shipcity.setValue("Hyderabad");
		ITextInput shipregion = ordersGroup.findElement(ITextInput.class,"shipregion");
		shipregion.setValue("AndhraPradesh");
		ITextInput shippostalcode = ordersGroup.findElement(ITextInput.class,"shippostalcode");
		shippostalcode.setValue("500081");
		ITextInput shipcountry = ordersGroup.findElement(ITextInput.class,"shipcountry");
		shipcountry.setValue("India");
		Grid orderDetailsGroup = bodyGroup.findElement(Grid.class, "Order_x0020_DetailsTable");
		orderDetailsGroup.pressInsert();
		IRow orderDetails = orderDetailsGroup.getSelectedRows().get(0);
		
		//orderDetails.getCellElement("Category").doubleClick();
		orderDetails.getCellElement("Category");
		IDropDown category = orderDetails.getCellElement(IDropDown.class,"Category",2);	
		category.selectByIndex(0);
		this.getContext().waitForIdle();
		orderDetails.getCellElement("ProductID").doubleClick();
		IDropDown productid = orderDetails.getCellElement(IDropDown.class, "ProductID",2);
		productid.selectByIndex(0);
		ITextInput unitPrice = orderDetails.getCellElement(ITextInput.class,"UnitPrice");
		unitPrice.setValue("14.40");
		ITextInput quantity = orderDetails.getCellElement(ITextInput.class, "Quantity");
		quantity.setValue("35");
		bodyGroup.findElement(IButton.class, "button1").click();		
		ordersApplication.close();
		ordersApplication.findDialog(IConfirm.class).clickNo();
	}
}
