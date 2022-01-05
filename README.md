# `use case`
Used for generating java code for calling Oracle Procedures ,Generated code is based on Spring jdbc

______
 ````<plugin>
                   <groupId>com.ertogrul.plugins</groupId>
                   <artifactId>generatedao-maven-plugin</artifactId>
                   <version>1.0.0</version>
                   <configuration>
                       <connectionUrl>jdbc:oracle:thin:@localhost:1521:togrulDB</connectionUrl>
                       <username>togrul</username>
                       <password>*******</password>
                       <packageName>TEST_PACKAGE</packageName>
                       <outputDirectory>${project.build.directory}</outputDirectory>
                       <className>TestDAO</className>
                       <procedures>
                           <procedure>TEST1</procedure>
                           <procedure>TEST2</procedure>
                           <procedure>TEST3</procedure>
                       </procedures>
                   </configuration>
               </plugin>
               
               
               