package com.ProjectOne.app;

import java.util.List;
import java.util.ArrayList;
import javax.enterprise.context.ApplicationScoped;
import com.ProjectOne.app.DataBlob;
import com.ProjectOne.app.DataQuery;

@ApplicationScoped
public class Database {

    private List<DataBlob>  dataList = new ArrayList<DataBlob>();
    private List<DataQuery> queryList = new ArrayList<DataQuery>();

    public void addData(String data, String source, String referrer) {
	DataBlob 	dataBlob = new DataBlob(data,source, referrer);
       	dataList.add( dataBlob) ;
    }

    public void addQuery(String query, String type, String skip) {
	DataQuery 	dataQuery = new DataQuery(query,type, skip);
	queryList.add(dataQuery);
    }


	public DataBlob getData() {
		
		return (dataList.isEmpty() ) ? null :dataList.remove(0);
	}
	public DataQuery getQuery() {
		return (queryList.isEmpty() ) ? null :queryList.remove(0);
	}
}

