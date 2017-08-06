/*
 * This file is part of hawkeye
 * Copyright (C) 2012-2013 Manoranjan Sahu, All Rights Reserved.
 *
 * This software is provided under the GNU GPL Version 2. In particular,
 *
 * 1) If you modify a source file, make a comment in it containing your name and the date.
 * 2) If you distribute a modified version, you must do it under the GPL 2.
 * 3) Developers are encouraged but not required to notify the hawkeye maintainers at abeautifulmind98@gmail.com
 * when they make a useful addition. It would be nice if significant contributions could be merged into the main distribution.
 *
 * A full copy of the license can be found in gpl.txt or online at
 * http://www.gnu.org/licenses/gpl.txt
 *
 * This program downloads data that is publicly available from www.espncricinfo.com
 * END_HEADER
 */
package org.espn.hawkeye.dao;

import org.apache.log4j.Logger;
import java.io.File;
import java.util.regex.Matcher;

import org.cricket.hawkeye.dao.DataPattern;
import org.cricket.hawkeye.dao.DefaultCricDataDAOImpl;
import org.cricket.hawkeye.dao.exception.DAOException;
import org.cricket.hawkeye.service.url.exception.URLServiceException;
import static org.espn.hawkeye.dao.ESPNCricInfoConstant.*;
/**
 *
 * @author manoranjan
 */

public class ESPNCricInfoDAOImpl extends DefaultCricDataDAOImpl {

    private static final Logger logger = Logger.getLogger(ESPNCricInfoDAOImpl.class);

  
    /**
     * This is the source of everything of hawkEye
     * @return
     * @throws org.cricket.hawkeye.dao.exception.DAOException
     */
    @Override
    public String findCountrysPath() throws DAOException{

        String result = null;
        
        String offLineCountriesStr = super.findCountrysPath();

        File offLineCountriesFile = new File(offLineCountriesStr);

        if(offLineCountriesFile.exists()){

            result =offLineCountriesStr;

        }else{

            result = COUNTRIES_URL;

        }
        
        return result;
    }

    /**
     * This has to be an online operation
     * @return
     * @throws DAOException
     */
    @Override
    public String findCountrysHTML() throws DAOException {

        String countriesURL = this.findCountrysPath();

        String result = null;

        try {

            result = this.getUrlService().fetch(countriesURL);

        } catch (URLServiceException ex) {

            logger.error("error occurred",ex);

            throw new DAOException(ex);

        }

        return result;
    }

   

    /**
     * This parses the country name from the countriesHTML
     * @param countryName
     * @return
     * @throws DAOException
     */
    @Override
    public String findCountryPath(String countryName) throws DAOException {

        String countryURL = null;

        Matcher countryMatcher = COUNTRY_PATTERN.matcher(super.findCountrysHTML());

        while (countryMatcher.find()) {

            String cName = countryMatcher.group(2);
            String countryId = countryMatcher.group(3);

            if (cName.equalsIgnoreCase(countryName)) {

                countryURL = ESPNCRICINFO +"/"+ countryName+"/content/player/country.html?country="+countryId;
                        

                break;
            }

        }

        if (countryURL == null) {

            throw new DAOException("could not find country URL for {" + countryName + "}");

        }

        return countryURL;

    }

    @Override
    public String findCountryHTML(String countryName) throws DAOException {

        String countryURL = this.findCountryPath(countryName);

        if (countryURL == null) {

            throw new DAOException("could not find country path for {" + countryName + "}");

        }

        String result = null;

        try {

            result = this.getUrlService().fetch(countryURL);

        } catch (URLServiceException ex) {

            logger.error("error occurred",ex);

            throw new DAOException(ex);

        }

        return result;

    }

    @Override
    public String findPlayerPath(String countryName, String playerName) throws DAOException {

        String playerURL = null;

        String countryHTML = this.findCountryHTML(countryName);

        if(countryHTML == null || countryHTML.isEmpty()){

            throw new DAOException("country not found {"+countryName+"}");

        }

        Matcher playerMatcher = PLAYER_PATTERN.matcher(countryHTML);

        while (playerMatcher.find()) {

            String playerURLsuffix = playerMatcher.group(1);

            String pName = playerMatcher.group(2);

            if(pName.equalsIgnoreCase(playerName)){

                playerURL = STATS_CRICINFO + playerURLsuffix + TEST_BATTING_INNINGS_SUFFIX;

                playerURL = playerURL.replaceAll("content", "engine");

                break;

            }

        }

        return playerURL;

    }

    @Override
    public String findPlayerHTML(String countryName, String playerName) throws DAOException {

        String playerURL = this.findPlayerPath(countryName,playerName);

        if (playerURL == null) {

            throw new DAOException("could not find player path for {" + playerName + "}");

        }

        String result = null;

        try {

            result = this.getUrlService().fetch(playerURL);

        } catch (URLServiceException ex) {

            logger.error("error occurred",ex);

            throw new DAOException(ex);

        }

        return result;

    }

    @Override
    public DataPattern getPlayerPattern() {
        DataPattern playerDataPattern = new DataPattern();
        playerDataPattern.setDesc("player");
        playerDataPattern.setPattern(PLAYER_PATTERN);
        return playerDataPattern;
    }

    @Override
    public DataPattern getCountryPattern() {
        DataPattern countryPattern = new DataPattern();
        countryPattern.setDesc("country");
        countryPattern.setPattern(COUNTRY_PATTERN);
        return countryPattern;
    }

   
    
    

    
    
}
