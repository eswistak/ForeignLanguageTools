/*

 * To change this license header, choose License Headers in Project Properties.

 * To change this template file, choose Tools | Templates

 * and open the template in the editor.

 */

package UI;



/**

 *

 * @author Hyung Kang

 */

public class TestItem {

    String name;

    String value;

    

    public TestItem(String name, String value) {

        this.name = name;

        this.value = value;

    }



    public String getName() {

        return name;

    }



    public String getValue() {

        return value;

    }

    

    @Override

    public String toString() {

        return this.name;

    }

    

}
