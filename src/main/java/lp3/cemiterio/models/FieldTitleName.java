/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lp3.cemiterio.models;

public class FieldTitleName {
    public final String title;
    public final String fieldName;
    public final String dbField;

    public FieldTitleName(String title, String fieldName, String dbField) {
        this.title = title;
        this.fieldName = fieldName;
        this.dbField = dbField;
    }
    
    @Override
    public String toString() {
        return title;
    }
}
