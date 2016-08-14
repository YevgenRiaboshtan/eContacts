package com.econtact.authWeb.app.testCode;

import java.io.IOException;
import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import javax.json.JsonBuilderFactory;

import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;

import com.google.common.net.InetAddresses;

@Named
@ApplicationScoped
public class TestBean implements Serializable {
	private static final long serialVersionUID = -292856430946906320L;

	private String i1;
	
	private Client client;
	
	@PostConstruct
	public void init() {
		client = TransportClient.builder().build().addTransportAddress(new InetSocketTransportAddress(InetAddresses.forString("127.0.0.1"), 9300));
		System.out.println("init");
	}
	
	
	public void search() {
		System.out.println(i1);
		GetResponse resp = client.prepareGet("index1", "indextype1", "indexid1").get();
		
		SearchResponse sresp = client.prepareSearch("index1")
		.execute()
		.actionGet();
		
		System.out.println(sresp.toString());
		i1="";
		
	}
	
	
	public void put() throws IOException {
		
		System.out.println(i1);
		XContentBuilder builder = XContentFactory.jsonBuilder()
				.startObject()
				.field("f1", i1)
				.field("f2", 0)
				.endObject();
		IndexResponse resp = client.prepareIndex("index1","indextype1").setSource(builder).execute().actionGet();
		i1 = "";
	}
	
	/**
	 * Method to return i1 
	 * @return the i1
	 */
	public String getI1() {
		return i1;
	}

	/**
	 * Method to set i1
	 * @param i1 the i1 to set
	 */
	public void setI1(String i1) {
		this.i1 = i1;
	}	
}
