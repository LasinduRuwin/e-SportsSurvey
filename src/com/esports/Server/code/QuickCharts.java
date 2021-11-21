package com.esports.Server.code;

import io.quickchart.QuickChart;

/**
 * This class contains the methods that sends necessary requests to QuikChart.io and receive the hart URLs
 * Base codes are in the QuickChart documentation :- https://quickchart.io/documentation/
 * @author Lasindu Ruwin
 */
public class QuickCharts {
    
    /**
     * This method is used to select between each available chart configurations
     * @param data dataset retrieved from database 
     * @param req chart name for selecting a chart
     * @return 
     * code for JSON request was provided in QuickChart documentation
     */
    private String chartConfigs (String [][] data , String req)
    {
        String chartconfig = null;
        
        if ("gamecchart".equals(req)){
            chartconfig = "{\n" +
                "  type: 'doughnut',\n" +
                "  data: {\n" +
                "    datasets: [\n" +
                "      {\n" +
                "        data: ["+Integer.parseInt(data[0][1])+", "+Integer.parseInt(data[1][1])+","+Integer.parseInt(data[2][1])+" ,"+Integer.parseInt(data[3][1])+""
                        + " ,"+Integer.parseInt(data[4][1])+" ,"+Integer.parseInt(data[5][1])+"], \n" +
                "        backgroundColor: [ 'rgb(255, 99, 132)', 'rgb(255, 159, 64)','rgb(255, 205, 86)', 'rgb(75, 192, 192)', 'rgb(54, 162, 235)', 'rgb(180, 109, 222)' ],\n" +
                "      },\n" +
                "    ],\n" +
                "    labels: ['"+data[0][0]+"', '"+data[1][0]+"', '"+data[2][0]+"','"+data[3][0]+"','"+data[4][0]+"','"+data[5][0]+"'],\n" +
                "  },\n" +
                "  options: {\n" +
                "    plugins: {\n" +
                "      datalabels: {\n" +
                "        formatter: (value) => {\n" +
                "          return value + '%';\n" +
                "        }\n" +
                "      }\n" +
                "    }\n" +
                "  }\n" +
                "}";
            return chartconfig;
        }
        if ("Stremer".equals(req))
        {
            chartconfig = "{\n" +
                "  type: 'doughnut',\n" +
                "  data: {\n" +
                "    datasets: [\n" +
                "      {\n" +
                "        data: ["+Integer.parseInt(data[0][1])+", "+Integer.parseInt(data[1][1])+"],\n" +
                "        backgroundColor: [ 'rgb(255, 99, 132)', 'rgb(255, 159, 64)', ],\n" +
                "      },\n" +
                "    ],\n" +
                "    labels: ['"+data[0][0]+"', '"+data[1][0]+"'],\n" +
                "  },\n" +
                "  options: {\n" +
                "    plugins: {\n" +
                "      datalabels: {\n" +
                "        formatter: (value) => {\n" +
                "          return value + '%';\n" +
                "        }\n" +
                "      }\n" +
                "    }\n" +
                "  }\n" +
                "}";
            return chartconfig;
        }
        if("Agv gametime".equals(req))
        {
            chartconfig = "{"
				+ "    type: 'bar',"
				+ "    data: {"
				+ "        labels: ['"+data[0][0]+"', '"+data[1][0]+"', '"+data[2][0]+"', '"+data[3][0]+"','"+data[4][0]+"','"+data[5][0]+"'],"
				+ "        datasets: ["
                                + "            {label: 'More than 5Hrs', data: ["+Integer.parseInt(data[0][1])+", "+Integer.parseInt(data[1][1])+", "+Integer.parseInt(data[2][1])+","+Integer.parseInt(data[3][1])+","+Integer.parseInt(data[4][1])+","+Integer.parseInt(data[5][1])+"]},"
                        	+ "            {label: 'Between 3 to 5 Hrs', data: ["+Integer.parseInt(data[0][2])+", "+Integer.parseInt(data[1][2])+", "+Integer.parseInt(data[2][2])+", "+Integer.parseInt(data[3][2])+", "+Integer.parseInt(data[4][2])+","+Integer.parseInt(data[5][2])+"]},"
				+ "            {label: 'Less than 2Hrs',data: ["+Integer.parseInt(data[0][3])+", "+Integer.parseInt(data[1][3])+", "+Integer.parseInt(data[2][3])+", "+Integer.parseInt(data[3][3])+","+Integer.parseInt(data[4][3])+","+Integer.parseInt(data[5][3])+"]} "
                                + "        ]"
				+ "    }"
				+ "}";
            return chartconfig;
        }
        
        return chartconfig;
    }
    
    /**
     * Generates and returns a chart URL by sending a request to quick chart web service 
     * base code provided by quick chart documentation
     * @param data will contain the data retrieved from database
     * @param req will contain the name of the chart which will determine the request
     * @return ShortURL of the chart as a String
     */
    public String getGameChart(String [][] data, String req)
    {
        QuickChart chart = new QuickChart();
		chart.setWidth(500);
		chart.setHeight(300);
		chart.setConfig(chartConfigs(data,req));  
		System.out.println("Game URL "+req+": \n"+chart.getShortUrl());
                String url = chart.getShortUrl();
                return url;
    }
 
}
