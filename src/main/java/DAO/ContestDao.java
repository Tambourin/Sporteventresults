/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import domain.Contest;
import java.util.List;

/**
 *
 * @author Olavi
 */
public interface ContestDao {
    void create(Contest contest);    
    void update(Contest contest);
    void delete(Integer key);
    Contest findById(Integer key);
    List<Contest> listAll();
}
