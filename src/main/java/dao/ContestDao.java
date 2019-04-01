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

    /**
     *
     * @param contest
     */
    void create(Contest contest);    

    /**
     *
     * @param contest
     */
    void update(Contest contest);

    /**
     *
     * @param key
     */
    void delete(Integer key);

    /**
     *
     * @param key
     * @return
     */
    Contest findById(Integer key);

    /**
     *
     * @return
     */
    List<Contest> listAll();
}
