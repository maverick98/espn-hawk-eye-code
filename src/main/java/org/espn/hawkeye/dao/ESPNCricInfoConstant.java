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
 * END_HEADER
 */


package org.espn.hawkeye.dao;

import java.util.regex.Pattern;

/**
 *
 * @author manoranjan
 */
public interface ESPNCricInfoConstant {

    String NAME = "ESPN";
    
    String about = "ESPNcricinfo (formerly CricInfo) is a sports news website exclusively for the game of cricket. "
            + "The site features news, articles, live coverage of cricket matches (including liveblogs and scorecards), "
            + "and StatsGuru, a database of historical matches and players from the 18th century to the present. "
            + "It is edited by Sambit Bal.\n" 
            + "The site, originally conceived in a pre-World Wide Web form in 1993 by Dr Simon King, "
            + "was acquired in 2002 by the Wisden Group—publishers of several notable Cricket magazines and "
            + "the Wisden Cricketers' Almanack. As part of an eventual breakup of the Wisden Group, it was sold to ESPN, "
            + "jointly owned by The Walt Disney Company and Hearst Corporation, in 2007."
            + "courtesy http://en.wikipedia.org/wiki/ESPNcricinfo";
    
    String ESPNCRICINFO =  "http://www.espncricinfo.com";

    String COUNTRIES_URL = new StringBuilder(ESPNCRICINFO).append("/ci/content/site/cricket_squads_teams/index.html").toString();

    String STATS_CRICINFO = "http://stats.espncricinfo.com";

    

    String TEST_BATTING_INNINGS_SUFFIX = "?class=1;template=results;type=batting;view=innings";

    String COUNTRY_PATTERN_STR = "<\\s*a\\s*href\\s*=\\s*\"http://www.espncricinfo.com/((\\w*)/content/current/team/\\d*.html)\"";

    Pattern COUNTRY_PATTERN = Pattern.compile(COUNTRY_PATTERN_STR);

    String PLAYER_PATTERN_STR = "<\\s*a\\s*href\\s*=\\s*\"(/\\w*/content/player/\\d*.html)\"\\s*class\\s*=\\s*\"\\w*\"\\s*>\\s*([\\w|\\s]*)\\s*<\\s*/\\s*a\\s*>";

    Pattern PLAYER_PATTERN = Pattern.compile(PLAYER_PATTERN_STR);

}
