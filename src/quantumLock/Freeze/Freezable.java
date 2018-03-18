/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package quantumLock.Freeze;

import city.cs.engine.Body;

import java.util.List;

/**
 *
 * @author pwajn
 */
public interface Freezable {
    void makeFixtures(Body body);
    List<Object> getExtraInfo();
    void setExtraInfo(List<Object> info);
}
