package pk;
// Import required java libraries
import java.io.*;
import java.net.URISyntaxException;

import javax.servlet.*;
import javax.servlet.http.*;

import net.oauth.OAuthException;

import org.eclipse.lyo.client.exception.JazzAuthErrorException;
import org.eclipse.lyo.client.exception.JazzAuthFailedException;
import org.eclipse.lyo.client.exception.ResourceNotFoundException;
import org.eclipse.lyo.client.exception.RootServicesException;
import org.eclipse.lyo.client.oslc.jazz.*;
import org.eclipse.lyo.client.oslc.*;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.HttpHeaders;
import javax.xml.parsers.ParserConfigurationException;

import net.oauth.OAuthException;

import org.apache.wink.client.ClientResponse;
import org.apache.wink.common.http.HttpStatus;
//import org.apache.wink.common.model.wadl.Link;
import org.apache.xerces.impl.xpath.regex.REUtil;
import org.eclipse.lyo.client.exception.JazzAuthErrorException;
import org.eclipse.lyo.client.exception.JazzAuthFailedException;
import org.eclipse.lyo.client.exception.ResourceNotFoundException;
import org.eclipse.lyo.client.exception.RootServicesException;
import org.eclipse.lyo.client.oslc.OSLCConstants;
import org.eclipse.lyo.client.oslc.jazz.JazzFormAuthClient;
import org.eclipse.lyo.client.oslc.jazz.JazzRootServicesHelper;
import org.eclipse.lyo.client.oslc.resources.Requirement;
import org.eclipse.lyo.client.oslc.resources.RmConstants;
import org.eclipse.lyo.client.oslc.resources.RmUtil;
import org.eclipse.lyo.client.oslc.resources.RequirementCollection;
import org.eclipse.lyo.oslc4j.core.OSLC4JUtils;
import org.eclipse.lyo.oslc4j.core.model.OslcMediaType;
import org.eclipse.lyo.oslc4j.core.model.ResourceShape;
import org.eclipse.lyo.oslc4j.core.model.Link;;


// Extend HttpServlet class
public class ConsumerApp extends HttpServlet {
 

  public ConsumerApp() {
      super();
      // TODO Auto-generated constructor stub
  } 
public void doGet(HttpServletRequest request,
                    HttpServletResponse response)
            throws ServletException, IOException
  {
      // Set response content type
      response.setContentType("text/html");
     
      
	try {
		System.out.println("Hello");
		JazzRootServicesHelper helper = new JazzRootServicesHelper( "https://hj-ibmibm4177.persistent.co.in:9443/rm" , OSLCConstants.OSLC_RM_V2 );

	      JazzFormAuthClient client = helper.initFormClient("sneha", "sneha", "https://hj-ibmibm4177.persistent.co.in:9443/jts"); 
	      System.out.println(client.login());
	      System.out.println(HttpStatus.OK+"  SttatusPrinted");
	      
	      System.out.println("prateek khare");
			
			System.out.println("prateek khare2");
			
			String serviceProviderUrl = client.lookupServiceProviderUrl(helper.getCatalogUrl(),"LogIn demo (Requirements)"); 
			System.out.println("khare 1");
			String requirementFactory = client.lookupCreationFactory(serviceProviderUrl, OSLCConstants.OSLC_RM_V2, OSLCConstants.RM_REQUIREMENT_TYPE); 
			System.out.println("prateek khare 2");			
			
			String queryCapability = client.lookupQueryCapability(serviceProviderUrl, OSLCConstants.OSLC_RM_V2, OSLCConstants.RM_REQUIREMENT_TYPE);
			
			System.out.println("prateek khare 3");
			ResourceShape featureInstanceShape = RmUtil.lookupRequirementsInstanceShapes( serviceProviderUrl, OSLCConstants.OSLC_RM_V2, OSLCConstants.RM_REQUIREMENT_TYPE, client, "Feature");
			System.out.println("prateek khare going to create a req");
		
				// Create pkreq001
				Requirement requirement = new Requirement();
				requirement.setInstanceShape(featureInstanceShape.getAbout());
				requirement.setTitle("pkreq001");
				
				//Put some primary text
				String primaryText = "My Primary Text for pkreq001";
				org.w3c.dom.Element obj = RmUtil.convertStringToHTML(primaryText);
				requirement.getExtendedProperties().put(RmConstants.PROPERTY_PRIMARY_TEXT, obj);
				
				requirement.setDescription("Created By EclipseLyo pkreq001");
				requirement.addImplementedBy(new Link(new URI("http://google.com"), "Link in pkreq001"));
				ClientResponse creationResponse = client.createResource(
						requirementFactory, requirement,OslcMediaType.APPLICATION_RDF_XML,OslcMediaType.APPLICATION_RDF_XML);
				String req01URL = creationResponse.getHeaders().getFirst(HttpHeaders.LOCATION);
				creationResponse.consumeContent();
				System.out.println("prateek khare has successfully created a req");
			
			
		} catch (RootServicesException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JazzAuthFailedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JazzAuthErrorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ResourceNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (OAuthException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} /*catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} */catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println(e.toString());
		} 
	}
}