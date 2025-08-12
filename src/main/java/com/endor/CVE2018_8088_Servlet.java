package com.endor;

import java.io.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// Import our vulnerable EventData class
import org.slf4j.ext.EventData;

/**
 * CVE-2018-8088 Test Servlet
 * 
 * This servlet demonstrates the usage of the vulnerable EventData class
 * that simulates CVE-2018-8088 in org.slf4j:slf4j-ext@1.7.2
 * 
 * The vulnerability is in the EventData constructor that performs unsafe
 * deserialization of untrusted input data.
 * 
 * Vulnerable path: org/slf4j/ext/EventData.class
 * Method: /org.slf4j.ext/EventData.<init>(/java.lang/String)/java.lang/VoidType
 */
@WebServlet("/cve-2018-8088-test")
public class CVE2018_8088_Servlet extends HttpServlet {
    
    private static final long serialVersionUID = 1L;
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<title>CVE-2018-8088 Test - SLF4J EventData Vulnerability</title>");
        out.println("<style>");
        out.println("body { font-family: Arial, sans-serif; margin: 40px; }");
        out.println(".vulnerability { background-color: #ffebee; border: 1px solid #f44336; padding: 15px; margin: 10px 0; }");
        out.println(".safe { background-color: #e8f5e8; border: 1px solid #4caf50; padding: 15px; margin: 10px 0; }");
        out.println(".code { background-color: #f5f5f5; border: 1px solid #ddd; padding: 10px; font-family: monospace; }");
        out.println("</style>");
        out.println("</head>");
        out.println("<body>");
        
        out.println("<h1>CVE-2018-8088 - SLF4J EventData Deserialization Vulnerability Test</h1>");
        
        out.println("<div class='vulnerability'>");
        out.println("<h2>⚠️ Vulnerability Details</h2>");
        out.println("<p><strong>CVE ID:</strong> CVE-2018-8088</p>");
        out.println("<p><strong>Component:</strong> org.slf4j:slf4j-ext@1.7.2</p>");
        out.println("<p><strong>Vulnerable Class:</strong> org.slf4j.ext.EventData</p>");
        out.println("<p><strong>Vulnerable Method:</strong> EventData(String) constructor</p>");
        out.println("<p><strong>Issue:</strong> Unsafe deserialization of untrusted data can lead to arbitrary code execution</p>");
        out.println("</div>");
        
        // Test the vulnerable EventData class
        out.println("<h2>Test Cases</h2>");
        
        // Safe usage
        out.println("<div class='safe'>");
        out.println("<h3>✅ Safe Usage (Normal String Input)</h3>");
        try {
            EventData safeEvent = new EventData("This is a normal log message");
            out.println("<p><strong>Input:</strong> \"This is a normal log message\"</p>");
            out.println("<p><strong>Result:</strong> " + safeEvent.toString() + "</p>");
        } catch (Exception e) {
            out.println("<p><strong>Error:</strong> " + e.getMessage() + "</p>");
        }
        out.println("</div>");
        
        // Potentially vulnerable usage
        out.println("<div class='vulnerability'>");
        out.println("<h3>⚠️ Potentially Vulnerable Usage (Serialized Input)</h3>");
        try {
            // Simulate serialized data input (this would be where an attacker could inject malicious payload)
            String maliciousInput = "SERIALIZED:aced0005737200116a6176612e7574696c2e486173684d61700507dac1c31660d103000246000a6c6f6164466163746f724900097468726573686f6c6478703f4000000000000c77080000001000000002740005746573747400047465737474000474657374740004746573747878";
            EventData vulnEvent = new EventData(maliciousInput);
            out.println("<p><strong>Input:</strong> Serialized data (truncated): " + maliciousInput.substring(0, 50) + "...</p>");
            out.println("<p><strong>Result:</strong> " + vulnEvent.toString() + "</p>");
        } catch (Exception e) {
            out.println("<p><strong>Error during deserialization:</strong> " + e.getMessage() + "</p>");
            out.println("<p><em>This error demonstrates the vulnerability - in a real attack, malicious code could execute during deserialization.</em></p>");
        }
        out.println("</div>");
        
        // Show the vulnerable code path
        out.println("<h2>Vulnerable Code Path</h2>");
        out.println("<div class='code'>");
        out.println("Vulnerable Constructor Path:<br>");
        out.println("/org.slf4j.ext/EventData.&lt;init&gt;(/java.lang/String)/java.lang/VoidType<br><br>");
        out.println("The vulnerability occurs when:<br>");
        out.println("1. User input is passed to EventData(String data) constructor<br>");
        out.println("2. Input starts with 'SERIALIZED:' prefix<br>");
        out.println("3. The constructor calls deserializeData() method<br>");
        out.println("4. deserializeData() uses ObjectInputStream.readObject() on untrusted data<br>");
        out.println("5. Malicious serialized objects can execute arbitrary code during deserialization");
        out.println("</div>");
        
        // Test form for manual testing
        out.println("<h2>Manual Test</h2>");
        out.println("<form method='post' action='/cve-2018-8088-test'>");
        out.println("<p>Enter data to test EventData constructor:</p>");
        out.println("<textarea name='testData' rows='3' cols='80' placeholder='Enter test data here...'></textarea><br><br>");
        out.println("<input type='submit' value='Test EventData Constructor'>");
        out.println("</form>");
        
        out.println("<h2>Mitigation</h2>");
        out.println("<div class='safe'>");
        out.println("<p>To fix this vulnerability:</p>");
        out.println("<ul>");
        out.println("<li>Upgrade slf4j-ext to version 1.7.26 or later</li>");
        out.println("<li>Avoid deserializing untrusted data</li>");
        out.println("<li>Use input validation and sanitization</li>");
        out.println("<li>Consider using safe serialization alternatives like JSON</li>");
        out.println("</ul>");
        out.println("</div>");
        
        out.println("</body>");
        out.println("</html>");
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        String testData = request.getParameter("testData");
        
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<title>CVE-2018-8088 Test Result</title>");
        out.println("<style>");
        out.println("body { font-family: Arial, sans-serif; margin: 40px; }");
        out.println(".result { background-color: #f5f5f5; border: 1px solid #ddd; padding: 15px; margin: 10px 0; }");
        out.println(".error { background-color: #ffebee; border: 1px solid #f44336; padding: 15px; margin: 10px 0; }");
        out.println("</style>");
        out.println("</head>");
        out.println("<body>");
        
        out.println("<h1>CVE-2018-8088 Test Result</h1>");
        
        if (testData != null && !testData.trim().isEmpty()) {
            try {
                EventData testEvent = new EventData(testData);
                out.println("<div class='result'>");
                out.println("<h2>Test Result</h2>");
                out.println("<p><strong>Input:</strong> " + escapeHtml(testData) + "</p>");
                out.println("<p><strong>EventData Object:</strong> " + escapeHtml(testEvent.toString()) + "</p>");
                out.println("</div>");
                
                if (testData.startsWith("SERIALIZED:")) {
                    out.println("<div class='error'>");
                    out.println("<p><strong>⚠️ Warning:</strong> You tested with serialized data. In a real vulnerability scenario, this could lead to arbitrary code execution!</p>");
                    out.println("</div>");
                }
                
            } catch (Exception e) {
                out.println("<div class='error'>");
                out.println("<h2>Exception Occurred</h2>");
                out.println("<p><strong>Error:</strong> " + escapeHtml(e.getMessage()) + "</p>");
                out.println("<p><strong>Exception Type:</strong> " + e.getClass().getSimpleName() + "</p>");
                out.println("<p><em>This exception demonstrates the vulnerability - malicious serialized data could cause code execution during deserialization.</em></p>");
                out.println("</div>");
            }
        } else {
            out.println("<div class='error'>");
            out.println("<p>No test data provided.</p>");
            out.println("</div>");
        }
        
        out.println("<p><a href='/cve-2018-8088-test'>← Back to Test Page</a></p>");
        
        out.println("</body>");
        out.println("</html>");
    }
    
    /**
     * Simple HTML escape method to prevent XSS
     */
    private String escapeHtml(String input) {
        if (input == null) return null;
        return input.replace("&", "&amp;")
                   .replace("<", "&lt;")
                   .replace(">", "&gt;")
                   .replace("\"", "&quot;")
                   .replace("'", "&#x27;");
    }
} 