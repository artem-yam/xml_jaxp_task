package ru.rsreu.Yamschikov.datalayer.oracledb;

import ru.rsreu.Yamschikov.datalayer.ScientificActivityDAO;
import ru.rsreu.Yamschikov.datalayer.data.scientificActivity.*;
import ru.rsreu.Yamschikov.datalayer.data.user.User;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class OracleScientificActivityDAO implements ScientificActivityDAO {
    private Connection connection;
    
    public OracleScientificActivityDAO(Connection connection) {
        this.connection = connection;
    }
    
    private List<ScientificActivity> formActivitiesList(ResultSet rs) {
        List<ScientificActivity> lst = new ArrayList<ScientificActivity>();
        
        OracleUserDAO userDAO = new OracleUserDAO(connection);
        
        try {
            while (rs.next()) {
                int activityId = rs.getInt(rs.findColumn("ID_RESEARCH"));
                String name = rs.getString(rs.findColumn("NAME"));
                int userId = rs.getInt(rs.findColumn("ID_OWNER"));
                String typeString =
                    rs.getString(rs.findColumn("RESEARCH_TYPE"));
                ScientificActivityTypeEnum type =
                    ScientificActivityTypeEnum.getType(typeString);
                Date date = rs.getDate(rs.findColumn("DATE_RESEARCH"));
                int rating = rs.getInt(rs.findColumn("RATING"));
                String description = rs.getString(rs.findColumn("DESCRIPTION"));
                
                User user = userDAO.getUserById(userId);
                
                ScientificActivity activity = null;
                PreparedStatement ps = null;
                ResultSet rs2 = null;
                
                try {
                    
                    switch (typeString) {
                        case "диссертация":
                            ps = connection.prepareStatement(
                                ("request.activities.dissertations.select.byid"));
                            ps.setInt(1, activityId);
                            rs2 = ps.executeQuery();
                            while (rs2.next()) {
                                String organization = rs2.getString(2);
                                DissertationSienceDegreeEnum degree =
                                    DissertationSienceDegreeEnum
                                        .getType(rs2.getString(3));
                                
                                Dissertation diss =
                                    new Dissertation(activityId, name, user,
                                        type, date, description,
                                        rating, organization, degree);
                                activity = (ScientificActivity) diss;
                            }
                            break;
                        case "мероприятие":
                            ps = connection
                                     .prepareStatement(
                                         ("request.activities.events.select.byid"));
                            ps.setInt(1, activityId);
                            rs2 = ps.executeQuery();
                            while (rs2.next()) {
                                EventTypeEnum eventType =
                                    EventTypeEnum.getType(rs2.getString(2));
                                String location = rs2.getString(3);
                                EventLocationTypeEnum locationType =
                                    EventLocationTypeEnum
                                        .getType(rs2.getString(4));
                                boolean participation = rs2.getBoolean(5);
                                
                                Event event =
                                    new Event(activityId, name, user, type,
                                        date, description, rating, eventType,
                                        location, locationType, participation);
                                activity = (ScientificActivity) event;
                            }
                            break;
                        case "патент":
                            ps = connection
                                     .prepareStatement(
                                         ("request.activities.patents.select.byid"));
                            ps.setInt(1, activityId);
                            rs2 = ps.executeQuery();
                            while (rs2.next()) {
                                PatentTypeEnum patentType =
                                    PatentTypeEnum.getType(rs2.getString(2));
                                String declarant = rs2.getString(3);
                                
                                Patent patent =
                                    new Patent(activityId, name, user, type,
                                        date, description, rating,
                                        patentType, declarant);
                                activity = (ScientificActivity) patent;
                            }
                            break;
                        case "публикация":
                            ps = connection.prepareStatement(
                                ("request.activities.publications.select.byid"));
                            ps.setInt(1, activityId);
                            rs2 = ps.executeQuery();
                            while (rs2.next()) {
                                PublicationTypeEnum publicationType =
                                    PublicationTypeEnum
                                        .getType(rs2.getString(2));
                                int pagesCount = rs2.getInt(3);
                                String publisherName = rs2.getString(4);
                                PublicationPublisherTypeEnum publisherType =
                                    PublicationPublisherTypeEnum
                                        .getType(rs2.getString(5));
                                
                                Publication publication =
                                    new Publication(activityId, name, user,
                                        type, date, description,
                                        rating, publicationType, pagesCount,
                                        publisherName, publisherType);
                                activity = (ScientificActivity) publication;
                            }
                            break;
                        case "награда":
                            ps = connection
                                     .prepareStatement(
                                         ("request.activities.awards.select.byid"));
                            ps.setInt(1, activityId);
                            rs2 = ps.executeQuery();
                            while (rs2.next()) {
                                AwardTypeEnum awardType =
                                    AwardTypeEnum.getType(rs2.getString(2));
                                AwardValueEnum awardValue =
                                    AwardValueEnum.getType(rs2.getString(3));
                                
                                Award award =
                                    new Award(activityId, name, user, type,
                                        date, description, rating, awardType,
                                        awardValue);
                                activity = (ScientificActivity) award;
                            }
                            break;
                        default:
                            break;
                        
                    }
                    
                } catch (SQLException e) {
                    System.err.println("DB connection error: " + e);
                } finally {
                    if (ps != null) {
                        ps.close();
                    } else {
                        System.err.println("Statement не создан");
                    }
                    
                    if (rs2 != null) {
                        rs2.close();
                    } else {
                        System.err.println(
                            "ошибка во время чтения из БД при формировании деятельности");
                    }
                    
                }
                
                if (!lst.contains(activity)) {
                    lst.add(activity);
                }
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lst;
    }
    
    private ScientificActivity formActivity(ResultSet rs) {
        ScientificActivity activity = null;
        
        OracleUserDAO userDAO = new OracleUserDAO(connection);
        
        try {
            while (rs.next()) {
                int activityId = rs.getInt(1);
                String name = rs.getString(2);
                int userId = rs.getInt(3);
                String typeString = rs.getString(4);
                ScientificActivityTypeEnum type =
                    ScientificActivityTypeEnum.getType(typeString);
                Date date = rs.getDate(5);
                int rating = rs.getInt(6);
                String description = rs.getString(7);
                
                User user = userDAO.getUserById(userId);
                
                PreparedStatement ps = null;
                ResultSet rs2 = null;
                
                try {
                    
                    switch (typeString) {
                        case "диссертация":
                            ps = connection.prepareStatement(
                                ("request.activities.dissertations.select.byid"));
                            ps.setInt(1, activityId);
                            rs2 = ps.executeQuery();
                            while (rs2.next()) {
                                String organization = rs2.getString(2);
                                DissertationSienceDegreeEnum degree =
                                    DissertationSienceDegreeEnum
                                        .getType(rs2.getString(3));
                                
                                Dissertation diss =
                                    new Dissertation(activityId, name, user,
                                        type, date, description,
                                        rating, organization, degree);
                                activity = (ScientificActivity) diss;
                            }
                            break;
                        case "мероприятие":
                            ps = connection
                                     .prepareStatement(
                                         ("request.activities.events.select.byid"));
                            ps.setInt(1, activityId);
                            rs2 = ps.executeQuery();
                            while (rs2.next()) {
                                EventTypeEnum eventType =
                                    EventTypeEnum.getType(rs2.getString(2));
                                String location = rs2.getString(3);
                                EventLocationTypeEnum locationType =
                                    EventLocationTypeEnum
                                        .getType(rs2.getString(4));
                                boolean participation = rs2.getBoolean(5);
                                
                                Event event =
                                    new Event(activityId, name, user, type,
                                        date, description, rating, eventType,
                                        location, locationType, participation);
                                activity = (ScientificActivity) event;
                            }
                            break;
                        case "патент":
                            ps = connection
                                     .prepareStatement(
                                         ("request.activities.patents.select.byid"));
                            ps.setInt(1, activityId);
                            rs2 = ps.executeQuery();
                            while (rs2.next()) {
                                PatentTypeEnum patentType =
                                    PatentTypeEnum.getType(rs2.getString(2));
                                String declarant = rs2.getString(3);
                                
                                Patent patent =
                                    new Patent(activityId, name, user, type,
                                        date, description, rating,
                                        patentType, declarant);
                                activity = (ScientificActivity) patent;
                            }
                            break;
                        case "публикация":
                            ps = connection.prepareStatement(
                                ("request.activities.publications.select.byid"));
                            ps.setInt(1, activityId);
                            rs2 = ps.executeQuery();
                            while (rs2.next()) {
                                PublicationTypeEnum publicationType =
                                    PublicationTypeEnum
                                        .getType(rs2.getString(2));
                                int pagesCount = rs2.getInt(3);
                                String publisherName = rs2.getString(4);
                                PublicationPublisherTypeEnum publisherType =
                                    PublicationPublisherTypeEnum
                                        .getType(rs2.getString(5));
                                
                                Publication publication =
                                    new Publication(activityId, name, user,
                                        type, date, description,
                                        rating, publicationType, pagesCount,
                                        publisherName, publisherType);
                                activity = (ScientificActivity) publication;
                            }
                            break;
                        case "награда":
                            ps = connection
                                     .prepareStatement(
                                         ("request.activities.awards.select.byid"));
                            ps.setInt(1, activityId);
                            rs2 = ps.executeQuery();
                            while (rs2.next()) {
                                AwardTypeEnum awardType =
                                    AwardTypeEnum.getType(rs2.getString(2));
                                AwardValueEnum awardValue =
                                    AwardValueEnum.getType(rs2.getString(3));
                                
                                Award award =
                                    new Award(activityId, name, user, type,
                                        date, description, rating, awardType,
                                        awardValue);
                                activity = (ScientificActivity) award;
                            }
                            break;
                        default:
                            break;
                        
                    }
                    
                } catch (SQLException e) {
                    System.err.println("DB connection error: " + e);
                } finally {
                    if (ps != null) {
                        ps.close();
                    } else {
                        System.err.println("Statement не создан");
                    }
                    if (rs2 != null) {
                        rs2.close();
                    } else {
                        System.err.println(
                            "ошибка во время чтения из БД при формировании деятельности");
                    }
                }
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return activity;
    }
    
    @Override
    public List<ScientificActivity> getAllActivities() {
        List<ScientificActivity> lst = new ArrayList<ScientificActivity>();
        
        try {
            Statement st = null;
            try {
                st = connection.createStatement();
                ResultSet rs = null;
                try {
                    rs = st.executeQuery(("request.activities.select.all"));
                    
                    lst = formActivitiesList(rs);
                    
                    if (lst.size() > 0) {
                        System.out.println(lst);
                    } else {
                        System.out.println("Not found");
                    }
                } finally {
                    if (rs != null) {
                        rs.close();
                    } else {
                        System.err.println(
                            "ошибка во время чтения из БД (Список всей деятельности)");
                    }
                }
            } finally {
                if (st != null) {
                    st.close();
                } else {
                    System.err.println("Statement не создан");
                }
            }
        } catch (SQLException e) {
            System.err.println("DB connection error: " + e);
        }
        
        return lst;
    }
    
    @Override
    public List<ScientificActivity> getUserActivity(int userId) {
        List<ScientificActivity> lst = new ArrayList<ScientificActivity>();
        
        try {
            PreparedStatement ps = null;
            ResultSet rs = null;
            try {
                
                ps = connection.prepareStatement(
                    ("request.activities.select.byuserid"));
                ps.setInt(1, userId);
                
                rs = ps.executeQuery();
                
                lst = formActivitiesList(rs);
                
                if (lst.size() > 0) {
                    System.out.println(lst);
                } else {
                    System.out.println("Not found");
                }
            } catch (SQLException e) {
                System.err.println("DB connection error: " + e);
            } finally {
                if (ps != null) {
                    ps.close();
                } else {
                    System.err.println("Statement не создан");
                }
                if (rs != null) {
                    rs.close();
                } else {
                    System.err.println(
                        "ошибка во время чтения из БД (Список всей деятельности)");
                }
            }
        } catch (SQLException e) {
            System.err.println("DB connection error: " + e);
        }
        
        return lst;
        
    }
    
    private List<ScientificActivity> addActivityToSearch(int userId,
                                                         String searchText) {
        List<ScientificActivity> lst = new ArrayList<ScientificActivity>();
        
        List<String> searchString =
            Arrays.asList(searchText.toLowerCase().split(" "));
        
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            for (int i = 0; i < searchString.size(); i++) {
                
                if (userId == 0) {
                    ps = connection.prepareStatement(
                        ("request.activities.dissertations.select.all.search"));
                    ps.setString(1, searchString.get(i));
                    ps.setString(2, searchString.get(i));
                } else {
                    ps = connection.prepareStatement(
                        ("request.activities.dissertations.select.all.search.specificUser"));
                    ps.setString(1, searchString.get(i));
                    ps.setString(2, searchString.get(i));
                    ps.setInt(3, userId);
                }
                
                rs = ps.executeQuery();
                
                lst.addAll(formActivitiesList(rs));
                
                ps.close();
                
                if (userId == 0) {
                    ps = connection
                             .prepareStatement(
                                 ("request.activities.events.select.all.search"));
                    ps.setString(1, searchString.get(i));
                    ps.setString(2, searchString.get(i));
                    ps.setString(3, searchString.get(i));
                } else {
                    ps = connection.prepareStatement(
                        ("request.activities.events.select.all.search.specificUser"));
                    ps.setString(1, searchString.get(i));
                    ps.setString(2, searchString.get(i));
                    ps.setString(3, searchString.get(i));
                    ps.setInt(4, userId);
                }
                
                rs = ps.executeQuery();
                
                lst.addAll(formActivitiesList(rs));
                
                ps.close();
                
                if (userId == 0) {
                    ps = connection
                             .prepareStatement(
                                 ("request.activities.patents.select.all.search"));
                    ps.setString(1, searchString.get(i));
                    ps.setString(2, searchString.get(i));
                } else {
                    ps = connection.prepareStatement(
                        ("request.activities.patents.select.all.search.specificUser"));
                    ps.setString(1, searchString.get(i));
                    ps.setString(2, searchString.get(i));
                    ps.setInt(3, userId);
                }
                
                rs = ps.executeQuery();
                
                lst.addAll(formActivitiesList(rs));
                
                ps.close();
                
                if (userId == 0) {
                    ps = connection
                             .prepareStatement(
                                 ("request.activities.awards.select.all.search"));
                    ps.setString(1, searchString.get(i));
                    ps.setString(2, searchString.get(i));
                } else {
                    ps = connection.prepareStatement(
                        ("request.activities.awards.select.all.search.specificUser"));
                    ps.setString(1, searchString.get(i));
                    ps.setString(2, searchString.get(i));
                    ps.setInt(3, userId);
                }
                
                rs = ps.executeQuery();
                
                lst.addAll(formActivitiesList(rs));
                
                ps.close();
                
                if (userId == 0) {
                    ps = connection.prepareStatement(
                        ("request.activities.publications.select.all.search"));
                    ps.setString(1, searchString.get(i));
                    ps.setString(2, searchString.get(i));
                    ps.setString(3, searchString.get(i));
                } else {
                    ps = connection.prepareStatement(
                        ("request.activities.publications.select.all.search.specificUser"));
                    ps.setString(1, searchString.get(i));
                    ps.setString(2, searchString.get(i));
                    ps.setString(3, searchString.get(i));
                    ps.setInt(4, userId);
                }
                
                rs = ps.executeQuery();
                
                lst.addAll(formActivitiesList(rs));
            }
        } catch (SQLException e) {
            System.err.println("Тип деятельности DB connection error: " + e);
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else {
                System.err
                    .println("ошибка во время чтения из БД Тип деятельности ");
            }
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else {
                System.err.println("Statement не создан Тип деятельности ");
            }
        }
        return lst;
    }
    
    @SuppressWarnings("unused")
    @Override
    public List<ScientificActivity> getSearсhedActivity(int userId,
                                                        String searchText) {
        List<ScientificActivity> lst = new ArrayList<ScientificActivity>();
        List<ScientificActivity> tempLst = new ArrayList<ScientificActivity>();
        
        List<String> searchString =
            Arrays.asList(searchText.toLowerCase().split(" "));
        
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            
            for (int i = 0; i < searchString.size(); i++) {
                
                if (userId == 0) {
                    ps = connection.prepareStatement(
                        ("request.activities.select.all.search"));
                    ps.setString(1, searchString.get(i));
                    ps.setString(2, searchString.get(i));
                    ps.setString(3, searchString.get(i));
                    ps.setString(4, searchString.get(i));
                    ps.setString(5, searchString.get(i));
                    ps.setString(6, searchString.get(i));
                    ps.setString(7, searchString.get(i));
                    ps.setString(8, searchString.get(i));
                } else {
                    ps = connection.prepareStatement(
                        ("request.activities.select.all.search.specificUser"));
                    ps.setString(1, searchString.get(i));
                    ps.setString(2, searchString.get(i));
                    ps.setString(3, searchString.get(i));
                    ps.setString(4, searchString.get(i));
                    ps.setString(5, searchString.get(i));
                    ps.setString(6, searchString.get(i));
                    ps.setString(7, searchString.get(i));
                    ps.setString(8, searchString.get(i));
                    ps.setInt(9, userId);
                }
                
                rs = ps.executeQuery();
                tempLst = formActivitiesList(rs);
                
                for (ScientificActivity act : tempLst) {
                    if (!lst.contains(act)) {
                        lst.add(act);
                    }
                }
                
                if (rs != null) {
                    try {
                        rs.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                } else {
                    System.err
                        .println("ошибка во время чтения из БД Деятельность");
                }
                if (ps != null) {
                    try {
                        ps.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                } else {
                    System.err.println("Statement не создан Деятельность");
                }
                
            }
            
            tempLst = addActivityToSearch(userId, searchText);
            
            for (ScientificActivity act : tempLst) {
                if (!lst.contains(act)) {
                    lst.add(act);
                }
            }
            
            if (lst.size() > 0) {
                System.out.println(lst);
            } else {
                System.out.println("Not found");
            }
            
        } catch (SQLException e) {
            System.err.println("Деятельность DB connection error: " + e);
        } finally {
        }
        return lst;
    }
    
    @Override
    public ScientificActivity getActivityById(int actId) {
        ScientificActivity act = null;
        try {
            PreparedStatement ps = null;
            try {
                ps = connection
                         .prepareStatement(("request.activities.select.byid"));
                ps.setInt(1, actId);
                ResultSet rs = null;
                try {
                    rs = ps.executeQuery();
                    
                    act = formActivity(rs);
                } finally {
                    if (rs != null) {
                        rs.close();
                    } else {
                        System.err.println("ошибка во время чтения из БД");
                    }
                }
            } finally {
                if (ps != null) {
                    ps.close();
                } else {
                    System.err.println("Statement не создан");
                }
            }
        } catch (SQLException e) {
            System.err.println("DB connection error: " + e);
        }
        return act;
    }
    
    @Override
    public int getNextId() {
        Statement st = null;
        ResultSet rs = null;
        int id = 1;
        try {
            st = connection.createStatement();
            rs = st.executeQuery(("request.activities.select.maxid"));
            while (rs.next()) {
                id = rs.getInt(1) + 1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (st != null) {
                try {
                    st.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else {
                System.err.println("ошибка во время чтения из БД");
            }
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else {
                System.err.println("ошибка во время чтения из БД");
            }
        }
        return id;
    }
    
    @Override
    public void createActivity(ScientificActivity activity) {
        PreparedStatement ps = null;
        
        try {
            int id = getNextId();
            
            ps = connection.prepareStatement(("request.activities.insert"));
            ps.setInt(1, id);
            ps.setString(2, activity.getName());
            ps.setInt(3, activity.getOwner().getId());
            ps.setString(4, activity.getType().getValue());
            ps.setDate(5, new java.sql.Date(activity.getDate().getTime()));
            ps.setInt(6, activity.getRating());
            ps.setString(7, activity.getDescription());
            
            ps.executeUpdate();
            
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            
            switch (activity.getType()) {
                case AWARD:
                    Award award = (Award) activity;
                    
                    ps = connection.prepareStatement(
                        ("request.activities.awards.insert"));
                    ps.setInt(1, id);
                    ps.setString(2, award.getAwardType().getValue());
                    ps.setString(3, award.getAwardValue().getValue());
                    
                    ps.executeUpdate();
                    
                    break;
                case DISSERTATION:
                    Dissertation diss = (Dissertation) activity;
                    
                    ps = connection.prepareStatement(
                        ("request.activities.dissertations.insert"));
                    ps.setInt(1, id);
                    ps.setString(2, diss.getOrganization());
                    ps.setString(3, diss.getSienceDegree().getValue());
                    
                    ps.executeUpdate();
                    
                    break;
                case EVENT:
                    Event event = (Event) activity;
                    
                    ps = connection.prepareStatement(
                        ("request.activities.events.insert"));
                    ps.setInt(1, id);
                    ps.setString(2, event.getEventType().getValue());
                    ps.setString(3, event.getLocation());
                    ps.setString(4, event.getLocationType().getValue());
                    
                    int partisipation = (event.isParticipation()) ? 1 : 0;
                    
                    ps.setInt(5, partisipation);
                    
                    ps.executeUpdate();
                    
                    break;
                case PATENT:
                    Patent patent = (Patent) activity;
                    
                    ps = connection.prepareStatement(
                        ("request.activities.patents.insert"));
                    ps.setInt(1, id);
                    ps.setString(2, patent.getPatentType().getValue());
                    ps.setString(3, patent.getDeclarant());
                    
                    ps.executeUpdate();
                    
                    break;
                case PUBLICATION:
                    Publication publication = (Publication) activity;
                    
                    ps = connection.prepareStatement(
                        ("request.activities.publications.insert"));
                    ps.setInt(1, id);
                    ps.setString(2,
                        publication.getPublicationType().getValue());
                    ps.setInt(3, publication.getPagesCount());
                    ps.setString(4, publication.getPublishingOfficeName());
                    ps.setString(5, publication.getPublisherType().getValue());
                    
                    ps.executeUpdate();
                    
                    break;
                default:
                    break;
            }
            
        } catch (SQLException e) {
            System.err.println("DB connection error: " + e);
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    @Override
    public void editActivity(ScientificActivity activity) {
        PreparedStatement ps = null;
        
        try {
            
            ps = connection.prepareStatement(("request.activities.update"));
            ps.setString(1, activity.getName());
            ps.setDate(2, new java.sql.Date(activity.getDate().getTime()));
            ps.setInt(3, activity.getRating());
            ps.setString(4, activity.getDescription());
            ps.setInt(5, activity.getId());
            
            ps.executeUpdate();
            
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            
            // ScientificActivityTypeEnum type =
            // ScientificActivityTypeEnum.getType(activity.getType());
            
            switch (activity.getType()) {
                case AWARD:
                    Award award = (Award) activity;
                    
                    ps = connection.prepareStatement(
                        ("request.activities.awards.update"));
                    ps.setString(1, award.getAwardType().getValue());
                    ps.setString(2, award.getAwardValue().getValue());
                    ps.setInt(3, award.getId());
                    
                    ps.executeUpdate();
                    
                    break;
                case DISSERTATION:
                    Dissertation diss = (Dissertation) activity;
                    
                    ps = connection.prepareStatement(
                        ("request.activities.dissertations.update"));
                    ps.setString(1, diss.getOrganization());
                    ps.setString(2, diss.getSienceDegree().getValue());
                    ps.setInt(3, diss.getId());
                    
                    ps.executeUpdate();
                    
                    break;
                case EVENT:
                    Event event = (Event) activity;
                    
                    ps = connection.prepareStatement(
                        ("request.activities.events.update"));
                    ps.setString(1, event.getEventType().getValue());
                    ps.setString(2, event.getLocation());
                    ps.setString(3, event.getLocationType().getValue());
                    int partisipation = (event.isParticipation()) ? 1 : 0;
                    ps.setInt(4, partisipation);
                    ps.setInt(5, event.getId());
                    
                    ps.executeUpdate();
                    
                    break;
                case PATENT:
                    Patent patent = (Patent) activity;
                    
                    ps = connection.prepareStatement(
                        ("request.activities.patents.update"));
                    ps.setString(1, patent.getPatentType().getValue());
                    ps.setString(2, patent.getDeclarant());
                    ps.setInt(3, patent.getId());
                    
                    ps.executeUpdate();
                    
                    break;
                case PUBLICATION:
                    Publication publication = (Publication) activity;
                    
                    ps = connection.prepareStatement(
                        ("request.activities.publications.update"));
                    ps.setString(1,
                        publication.getPublicationType().getValue());
                    ps.setInt(2, publication.getPagesCount());
                    ps.setString(3, publication.getPublishingOfficeName());
                    ps.setString(4, publication.getPublisherType().getValue());
                    ps.setInt(5, publication.getId());
                    
                    ps.executeUpdate();
                    
                    break;
                default:
                    break;
            }
            
        } catch (SQLException e) {
            System.err.println("DB connection error: " + e);
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    @Override
    public void deleteActivity(int actId) {
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(("request.activities.delete"));
            ps.setInt(1, actId);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("DB connection error: " + e);
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    @Override
    public List<ScientificActivity> getReportActivities(String dateFrom,
                                                        String dateTo,
                                                        int userId) {
        List<ScientificActivity> lst = new ArrayList<ScientificActivity>();
        
        Date date1 = null;
        Date date2 = null;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            date1 = format.parse(dateFrom);
            date2 = format.parse(dateTo);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        
        try {
            PreparedStatement ps = null;
            ResultSet rs = null;
            try {
                
                if (userId == 0) {
                    ps = connection.prepareStatement(
                        ("request.activities.select.bydate"));
                } else {
                    ps = connection
                             .prepareStatement(
                                 ("request.activities.select.bydate.specificUser"));
                    ps.setInt(3, userId);
                }
                ps.setDate(1, new java.sql.Date(date1.getTime()));
                ps.setDate(2, new java.sql.Date(date2.getTime()));
                
                rs = ps.executeQuery();
                
                lst = formActivitiesList(rs);
                
                if (lst.size() > 0) {
                    System.out.println(lst);
                } else {
                    System.out.println("Not found");
                }
            } catch (SQLException e) {
                System.err.println("DB connection error: " + e);
            } finally {
                if (ps != null) {
                    ps.close();
                } else {
                    System.err.println("Statement не создан");
                }
                if (rs != null) {
                    rs.close();
                } else {
                    System.err.println(
                        "ошибка во время чтения из БД (Список всей деятельности)");
                }
            }
        } catch (SQLException e) {
            System.err.println("DB connection error: " + e);
        }
        
        return lst;
    }
    
    private ResultSet getFilledRequestWithoutDate(String reportType) {
        ResultSet rs = null;
        PreparedStatement ps = null;
        Statement st = null;
        
        try {
            switch (reportType) {
                case ("allDissertations"):
                    st = connection.createStatement();
                    rs = st.executeQuery(
                        ("request.activities.dissertations.select.all"));
                    break;
                case ("doctorDissertations"):
                    ps = connection
                             .prepareStatement(
                                 ("request.activities.dissertations.select.degree"));
                    ps.setString(1, "доктор наук");
                    break;
                case ("candidateDissertations"):
                    ps = connection
                             .prepareStatement(
                                 ("request.activities.dissertations.select.degree"));
                    ps.setString(1, "кандидат наук");
                    break;
                case ("RSREUDissertations"):
                    ps = connection.prepareStatement(
                        ("request.activities.dissertations.select.organization"));
                    ps.setString(1, "РГРТУ");
                    break;
                case ("otherDissertations"):
                    ps = connection.prepareStatement(
                        ("request.activities.dissertations.select.organization.other"));
                    ps.setString(1, "РГРТУ");
                    break;
                case ("allPublications"):
                    st = connection.createStatement();
                    rs = st.executeQuery(
                        ("request.activities.publications.select.all"));
                    break;
                case ("foreignPublications"):
                    ps = connection.prepareStatement(
                        ("request.activities.publications.select.publisherType"));
                    ps.setString(1, "зарубежное издательство");
                    break;
                case ("russianPublications"):
                    ps = connection.prepareStatement(
                        ("request.activities.publications.select.publisherType"));
                    ps.setString(1, "российское издательство");
                    break;
                case ("universityPublications"):
                    ps = connection.prepareStatement(
                        ("request.activities.publications.select.publisherType"));
                    ps.setString(1, "вузовское издательство");
                    break;
                case ("allMonographs"):
                    ps = connection
                             .prepareStatement(
                                 ("request.activities.publications.select.bytype"));
                    ps.setString(1, "монография");
                    break;
                case ("foreignMonographs"):
                    ps = connection.prepareStatement(
                        ("request.activities.publications.select.bytype.publisherType"));
                    ps.setString(1, "монография");
                    ps.setString(2, "зарубежное издательство");
                    break;
                case ("russianMonographs"):
                    ps = connection.prepareStatement(
                        ("request.activities.publications.select.bytype.publisherType"));
                    ps.setString(1, "монография");
                    ps.setString(2, "российское издательство");
                    break;
                case ("universityMonographs"):
                    ps = connection.prepareStatement(
                        ("request.activities.publications.select.bytype.publisherType"));
                    ps.setString(1, "монография");
                    ps.setString(2, "вузовское издательство");
                    break;
                case ("allCollections"):
                    ps = connection
                             .prepareStatement(
                                 ("request.activities.publications.select.bytype"));
                    ps.setString(1, "сборник научных трудов");
                    break;
                case ("foreignCollections"):
                    ps = connection.prepareStatement(
                        ("request.activities.publications.select.bytype.publisherType"));
                    ps.setString(1, "сборник научных трудов");
                    ps.setString(2, "зарубежное издательство");
                    break;
                case ("russianCollections"):
                    ps = connection.prepareStatement(
                        ("request.activities.publications.select.bytype.publisherType"));
                    ps.setString(1, "сборник научных трудов");
                    ps.setString(2, "российское издательство");
                    break;
                case ("universityCollections"):
                    ps = connection.prepareStatement(
                        ("request.activities.publications.select.bytype.publisherType"));
                    ps.setString(1, "сборник научных трудов");
                    ps.setString(2, "вузовское издательство");
                    break;
                case ("allTextbooks"):
                    ps = connection
                             .prepareStatement(
                                 ("request.activities.publications.select.bytype"));
                    ps.setString(1, "учебник");
                    break;
                case ("ETextbooks"):
                    ps = connection.prepareStatement(
                        ("request.activities.publications.select.bytype.specific"));
                    ps.setString(1, "учебник");
                    ps.setString(2, "электрон");
                    ps.setString(3, "электрон");
                    ps.setString(4, "электрон");
                    break;
                case ("allSchoolbooks"):
                    ps = connection
                             .prepareStatement(
                                 ("request.activities.publications.select.bytype"));
                    ps.setString(1, "учебное пособие");
                    break;
                case ("defMinRFSchoolbooks"):
                    ps = connection.prepareStatement(
                        ("request.activities.publications.select.bytype.specific"));
                    ps.setString(1, "учебник");
                    ps.setString(2, "минобрнауки");
                    ps.setString(3, "минобрнауки");
                    ps.setString(4, "минобрнауки");
                    break;
                case ("YMOSchoolbooks"):
                    ps = connection.prepareStatement(
                        ("request.activities.publications.select.bytype.specific"));
                    ps.setString(1, "учебник");
                    ps.setString(2, "УМО");
                    ps.setString(3, "УМО");
                    ps.setString(4, "УМО");
                    break;
                case ("HMСSchoolbooks"):
                    ps = connection.prepareStatement(
                        ("request.activities.publications.select.bytype.specific"));
                    ps.setString(1, "учебник");
                    ps.setString(2, "НМС");
                    ps.setString(3, "НМС");
                    ps.setString(4, "НМС");
                    break;
                case ("federalSchoolbooks"):
                    ps = connection.prepareStatement(
                        ("request.activities.publications.select.bytype.specific"));
                    ps.setString(1, "учебник");
                    ps.setString(2, "федерал");
                    ps.setString(3, "федерал");
                    ps.setString(4, "федерал");
                    break;
                case ("allArticles"):
                    ps = connection
                             .prepareStatement(
                                 ("request.activities.publications.select.bytype"));
                    ps.setString(1, "статья");
                    break;
                case ("BAKArticles"):
                    ps = connection.prepareStatement(
                        ("request.activities.publications.select.bytype.specific"));
                    ps.setString(1, "статья");
                    ps.setString(2, "ВАК");
                    ps.setString(3, "ВАК");
                    ps.setString(4, "ВАК");
                    break;
                case ("foreignArticles"):
                    ps = connection.prepareStatement(
                        ("request.activities.publications.select.bytype.publisherType"));
                    ps.setString(1, "статья");
                    ps.setString(2, "зарубежное издательство");
                    break;
                case ("russianArticles"):
                    ps = connection.prepareStatement(
                        ("request.activities.publications.select.bytype.publisherType"));
                    ps.setString(1, "статья");
                    ps.setString(2, "российское издательство");
                    break;
                case ("universityArticles"):
                    ps = connection.prepareStatement(
                        ("request.activities.publications.select.bytype.publisherType"));
                    ps.setString(1, "статья");
                    ps.setString(2, "вузовское издательство");
                    break;
                case ("allPatents"):
                    st = connection.createStatement();
                    rs = st.executeQuery(
                        ("request.activities.patents.select.all"));
                    break;
                case ("RSREUPatents"):
                    ps = connection
                             .prepareStatement(
                                 ("request.activities.patents.select.bydeclarant"));
                    ps.setString(1, "РГРТУ");
                    break;
                case ("russianPatents"):
                    ps = connection.prepareStatement(
                        ("request.activities.patents.select.bytype"));
                    ps.setString(1, "патент Росcии");
                    break;
                case ("foreignPatents"):
                    ps = connection.prepareStatement(
                        ("request.activities.patents.select.bytype"));
                    ps.setString(1, "зарубежный патент");
                    break;
                case ("supportedPatents"):
                    ps = connection.prepareStatement(
                        ("request.activities.patents.select.bytype"));
                    ps.setString(1, "поддерживаемый патент");
                    break;
                case ("allEvents"):
                    st = connection.createStatement();
                    rs = st.executeQuery(
                        ("request.activities.events.select.all"));
                    break;
                case ("allExhibitions"):
                    ps = connection.prepareStatement(
                        ("request.activities.events.select.bytype"));
                    ps.setString(1, "выставка");
                    break;
                case ("internationalExhibitions"):
                    ps = connection.prepareStatement(
                        ("request.activities.events.select.bytype.bylocationtype"));
                    ps.setString(1, "выставка");
                    ps.setString(2, "международная");
                    break;
                case ("allRussianExhibitions"):
                    ps = connection.prepareStatement(
                        ("request.activities.events.select.bytype.bylocationtype"));
                    ps.setString(1, "выставка");
                    ps.setString(2, "всероссийская");
                    break;
                case ("interregionalExhibitions"):
                    ps = connection.prepareStatement(
                        ("request.activities.events.select.bytype.bylocationtype"));
                    ps.setString(1, "выставка");
                    ps.setString(2, "межрегиональная");
                    break;
                case ("universityExhibitions"):
                    ps = connection.prepareStatement(
                        ("request.activities.events.select.bytype.bylocationtype"));
                    ps.setString(1, "выставка");
                    ps.setString(2, "на базе вуза");
                    break;
                case ("allExhibits"):
                    ps = connection.prepareStatement(
                        ("request.activities.events.select.bytype.participation"));
                    ps.setString(1, "выставка");
                    ps.setBoolean(2, true);
                    break;
                case ("internationalExhibits"):
                    ps = connection.prepareStatement(
                        ("request.activities.events.select.bytype.bylocationtype.participation"));
                    ps.setString(1, "выставка");
                    ps.setString(2, "международная");
                    ps.setBoolean(3, true);
                    break;
                case ("allRussianExhibits"):
                    ps = connection.prepareStatement(
                        ("request.activities.events.select.bytype.bylocationtype.participation"));
                    ps.setString(1, "выставка");
                    ps.setString(2, "всероссийская");
                    ps.setBoolean(3, true);
                    break;
                case ("interregionalExhibits"):
                    ps = connection.prepareStatement(
                        ("request.activities.events.select.bytype.bylocationtype.participation"));
                    ps.setString(1, "выставка");
                    ps.setString(2, "межрегиональная");
                    ps.setBoolean(3, true);
                    break;
                case ("universityExhibits"):
                    ps = connection.prepareStatement(
                        ("request.activities.events.select.bytype.bylocationtype.participation"));
                    ps.setString(1, "выставка");
                    ps.setString(2, "на базе вуза");
                    ps.setBoolean(3, true);
                    break;
                case ("allConferences"):
                    ps = connection.prepareStatement(
                        ("request.activities.events.select.bytype"));
                    ps.setString(1, "конференция");
                    break;
                case ("internationalConferences"):
                    ps = connection.prepareStatement(
                        ("request.activities.events.select.bytype.bylocationtype"));
                    ps.setString(1, "конференция");
                    ps.setString(2, "международная");
                    break;
                case ("allRussianConferences"):
                    ps = connection.prepareStatement(
                        ("request.activities.events.select.bytype.bylocationtype"));
                    ps.setString(1, "конференция");
                    ps.setString(2, "всероссийская");
                    break;
                case ("interregionalConferences"):
                    ps = connection.prepareStatement(
                        ("request.activities.events.select.bytype.bylocationtype"));
                    ps.setString(1, "конференция");
                    ps.setString(2, "межрегиональная");
                    break;
                case ("universityConferences"):
                    ps = connection.prepareStatement(
                        ("request.activities.events.select.bytype.bylocationtype"));
                    ps.setString(1, "конференция");
                    ps.setString(2, "на базе вуза");
                    break;
                case ("allReportsThesises"):
                    ps = connection.prepareStatement(
                        ("request.activities.events.select.bytype.participation"));
                    ps.setString(1, "конференция");
                    ps.setBoolean(2, true);
                    break;
                case ("internationalReportsThesises"):
                    ps = connection.prepareStatement(
                        ("request.activities.events.select.bytype.bylocationtype.participation"));
                    ps.setString(1, "конференция");
                    ps.setString(2, "международная");
                    ps.setBoolean(3, true);
                    break;
                case ("allRussianReportsThesises"):
                    ps = connection.prepareStatement(
                        ("request.activities.events.select.bytype.bylocationtype.participation"));
                    ps.setString(1, "конференция");
                    ps.setString(2, "всероссийская");
                    ps.setBoolean(3, true);
                    break;
                case ("interregionalReportsThesises"):
                    ps = connection.prepareStatement(
                        ("request.activities.events.select.bytype.bylocationtype.participation"));
                    ps.setString(1, "конференция");
                    ps.setString(2, "межрегиональная");
                    ps.setBoolean(3, true);
                    break;
                case ("universityReportsThesises"):
                    ps = connection.prepareStatement(
                        ("request.activities.events.select.bytype.bylocationtype.participation"));
                    ps.setString(1, "конференция");
                    ps.setString(2, "на базе вуза");
                    ps.setBoolean(3, true);
                    break;
                case ("allAwards"):
                    st = connection.createStatement();
                    rs = st.executeQuery(
                        ("request.activities.awards.select.all"));
                    break;
                case ("internationalAwards"):
                    ps = connection.prepareStatement(
                        ("request.activities.awards.select.byvalue"));
                    ps.setString(1, "международный уровень");
                    break;
                case ("federalAwards"):
                    ps = connection.prepareStatement(
                        ("request.activities.awards.select.byvalue"));
                    ps.setString(1, "федеральный уровень");
                    break;
                case ("regionalAwards"):
                    ps = connection.prepareStatement(
                        ("request.activities.awards.select.byvalue"));
                    ps.setString(1, "региональный уровень");
                    break;
                case ("prizeAwards"):
                    ps = connection.prepareStatement(
                        ("request.activities.awards.select.bytype"));
                    ps.setString(1, "премия");
                    break;
                case ("diplomaAwards"):
                    ps = connection.prepareStatement(
                        ("request.activities.awards.select.bytype"));
                    ps.setString(1, "диплом");
                    break;
                case ("medalAwards"):
                    ps = connection.prepareStatement(
                        ("request.activities.awards.select.bytype"));
                    ps.setString(1, "медаль");
                    break;
                default:
                    break;
            }
            
            if (ps != null) {
                rs = ps.executeQuery();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return rs;
    }
    
    private ResultSet getFilledRequestWithDate(String reportType,
                                               String dateFrom, String dateTo) {
        Date date1 = null;
        Date date2 = null;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            date1 = format.parse(dateFrom);
            date2 = format.parse(dateTo);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        
        java.sql.Date dateSql1 = new java.sql.Date(date1.getTime());
        java.sql.Date dateSql2 = new java.sql.Date(date2.getTime());
        
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            switch (reportType) {
                case ("allDissertations"):
                    ps = connection
                             .prepareStatement(
                                 ("request.activities.dissertations.select.all.bydate"));
                    ps.setDate(1, dateSql1);
                    ps.setDate(2, dateSql2);
                    break;
                case ("doctorDissertations"):
                    ps = connection.prepareStatement(
                        ("request.activities.dissertations.select.degree.bydate"));
                    ps.setString(1, "доктор наук");
                    ps.setDate(2, dateSql1);
                    ps.setDate(3, dateSql2);
                    break;
                case ("candidateDissertations"):
                    ps = connection.prepareStatement(
                        ("request.activities.dissertations.select.degree.bydate"));
                    ps.setString(1, "кандидат наук");
                    ps.setDate(2, dateSql1);
                    ps.setDate(3, dateSql2);
                    break;
                case ("RSREUDissertations"):
                    ps = connection.prepareStatement(
                        ("request.activities.dissertations.select.organization.bydate"));
                    ps.setString(1, "РГРТУ");
                    ps.setDate(2, dateSql1);
                    ps.setDate(3, dateSql2);
                    break;
                case ("otherDissertations"):
                    ps = connection.prepareStatement(
                        ("request.activities.dissertations.select.organization.other.bydate"));
                    ps.setString(1, "РГРТУ");
                    ps.setDate(2, dateSql1);
                    ps.setDate(3, dateSql2);
                    break;
                case ("allPublications"):
                    ps = connection
                             .prepareStatement(
                                 ("request.activities.publications.select.all.bydate"));
                    ps.setDate(1, dateSql1);
                    ps.setDate(2, dateSql2);
                    break;
                case ("foreignPublications"):
                    ps = connection.prepareStatement(
                        ("request.activities.publications.select.publisherType.bydate"));
                    ps.setString(1, "зарубежное издательство");
                    ps.setDate(2, dateSql1);
                    ps.setDate(3, dateSql2);
                    break;
                case ("russianPublications"):
                    ps = connection.prepareStatement(
                        ("request.activities.publications.select.publisherType.bydate"));
                    ps.setString(1, "российское издательство");
                    ps.setDate(2, dateSql1);
                    ps.setDate(3, dateSql2);
                    break;
                case ("universityPublications"):
                    ps = connection.prepareStatement(
                        ("request.activities.publications.select.publisherType.bydate"));
                    ps.setString(1, "вузовское издательство");
                    ps.setDate(2, dateSql1);
                    ps.setDate(3, dateSql2);
                    break;
                case ("allMonographs"):
                    ps = connection.prepareStatement(
                        ("request.activities.publications.select.bytype.bydate"));
                    ps.setString(1, "монография");
                    ps.setDate(2, dateSql1);
                    ps.setDate(3, dateSql2);
                    break;
                case ("foreignMonographs"):
                    ps = connection.prepareStatement(
                        ("request.activities.publications.select.bytype.publisherType.bydate"));
                    ps.setString(1, "монография");
                    ps.setString(2, "зарубежное издательство");
                    ps.setDate(3, dateSql1);
                    ps.setDate(4, dateSql2);
                    break;
                case ("russianMonographs"):
                    ps = connection.prepareStatement(
                        ("request.activities.publications.select.bytype.publisherType.bydate"));
                    ps.setString(1, "монография");
                    ps.setString(2, "российское издательство");
                    ps.setDate(3, dateSql1);
                    ps.setDate(4, dateSql2);
                    break;
                case ("universityMonographs"):
                    ps = connection.prepareStatement(
                        ("request.activities.publications.select.bytype.publisherType.bydate"));
                    ps.setString(1, "монография");
                    ps.setString(2, "вузовское издательство");
                    ps.setDate(3, dateSql1);
                    ps.setDate(4, dateSql2);
                    break;
                case ("allCollections"):
                    ps = connection.prepareStatement(
                        ("request.activities.publications.select.bytype.bydate"));
                    ps.setString(1, "сборник научных трудов");
                    ps.setDate(2, dateSql1);
                    ps.setDate(3, dateSql2);
                    break;
                case ("foreignCollections"):
                    ps = connection.prepareStatement(
                        ("request.activities.publications.select.bytype.publisherType.bydate"));
                    ps.setString(1, "сборник научных трудов");
                    ps.setString(2, "зарубежное издательство");
                    ps.setDate(3, dateSql1);
                    ps.setDate(4, dateSql2);
                    break;
                case ("russianCollections"):
                    ps = connection.prepareStatement(
                        ("request.activities.publications.select.bytype.publisherType.bydate"));
                    ps.setString(1, "сборник научных трудов");
                    ps.setString(2, "российское издательство");
                    ps.setDate(3, dateSql1);
                    ps.setDate(4, dateSql2);
                    break;
                case ("universityCollections"):
                    ps = connection.prepareStatement(
                        ("request.activities.publications.select.bytype.publisherType.bydate"));
                    ps.setString(1, "сборник научных трудов");
                    ps.setString(2, "вузовское издательство");
                    ps.setDate(3, dateSql1);
                    ps.setDate(4, dateSql2);
                    break;
                case ("allTextbooks"):
                    ps = connection.prepareStatement(
                        ("request.activities.publications.select.bytype.bydate"));
                    ps.setString(1, "учебник");
                    ps.setDate(2, dateSql1);
                    ps.setDate(3, dateSql2);
                    break;
                case ("ETextbooks"):
                    ps = connection.prepareStatement(
                        ("request.activities.publications.select.bytype.specific.bydate"));
                    ps.setString(1, "учебник");
                    ps.setString(2, "электрон");
                    ps.setString(3, "электрон");
                    ps.setString(4, "электрон");
                    ps.setDate(5, dateSql1);
                    ps.setDate(6, dateSql2);
                    break;
                case ("allSchoolbooks"):
                    ps = connection.prepareStatement(
                        ("request.activities.publications.select.bytype.bydate"));
                    ps.setString(1, "учебное пособие");
                    ps.setDate(2, dateSql1);
                    ps.setDate(3, dateSql2);
                    break;
                case ("defMinRFSchoolbooks"):
                    ps = connection.prepareStatement(
                        ("request.activities.publications.select.bytype.specific.bydate"));
                    ps.setString(1, "учебник");
                    ps.setString(2, "минобрнауки");
                    ps.setString(3, "минобрнауки");
                    ps.setString(4, "минобрнауки");
                    ps.setDate(5, dateSql1);
                    ps.setDate(6, dateSql2);
                    break;
                case ("YMOSchoolbooks"):
                    ps = connection.prepareStatement(
                        ("request.activities.publications.select.bytype.specific.bydate"));
                    ps.setString(1, "учебник");
                    ps.setString(2, "УМО");
                    ps.setString(3, "УМО");
                    ps.setString(4, "УМО");
                    ps.setDate(5, dateSql1);
                    ps.setDate(6, dateSql2);
                    break;
                case ("HMСSchoolbooks"):
                    ps = connection.prepareStatement(
                        ("request.activities.publications.select.bytype.specific.bydate"));
                    ps.setString(1, "учебник");
                    ps.setString(2, "НМС");
                    ps.setString(3, "НМС");
                    ps.setString(4, "НМС");
                    ps.setDate(5, dateSql1);
                    ps.setDate(6, dateSql2);
                    break;
                case ("federalSchoolbooks"):
                    ps = connection.prepareStatement(
                        ("request.activities.publications.select.bytype.specific.bydate"));
                    ps.setString(1, "учебник");
                    ps.setString(2, "федерал");
                    ps.setString(3, "федерал");
                    ps.setString(4, "федерал");
                    ps.setDate(5, dateSql1);
                    ps.setDate(6, dateSql2);
                    break;
                case ("allArticles"):
                    ps = connection.prepareStatement(
                        ("request.activities.publications.select.bytype.bydate"));
                    ps.setString(1, "статья");
                    ps.setDate(2, dateSql1);
                    ps.setDate(3, dateSql2);
                    break;
                case ("BAKArticles"):
                    ps = connection.prepareStatement(
                        ("request.activities.publications.select.bytype.specific.bydate"));
                    ps.setString(1, "статья");
                    ps.setString(2, "ВАК");
                    ps.setString(3, "ВАК");
                    ps.setString(4, "ВАК");
                    ps.setDate(5, dateSql1);
                    ps.setDate(6, dateSql2);
                    break;
                case ("foreignArticles"):
                    ps = connection.prepareStatement(
                        ("request.activities.publications.select.bytype.publisherType.bydate"));
                    ps.setString(1, "статья");
                    ps.setString(2, "зарубежное издательство");
                    ps.setDate(3, dateSql1);
                    ps.setDate(4, dateSql2);
                    break;
                case ("russianArticles"):
                    ps = connection.prepareStatement(
                        ("request.activities.publications.select.bytype.publisherType.bydate"));
                    ps.setString(1, "статья");
                    ps.setString(2, "российское издательство");
                    ps.setDate(3, dateSql1);
                    ps.setDate(4, dateSql2);
                    break;
                case ("universityArticles"):
                    ps = connection.prepareStatement(
                        ("request.activities.publications.select.bytype.publisherType.bydate"));
                    ps.setString(1, "статья");
                    ps.setString(2, "вузовское издательство");
                    ps.setDate(3, dateSql1);
                    ps.setDate(4, dateSql2);
                    break;
                case ("allPatents"):
                    ps = connection
                             .prepareStatement(
                                 ("request.activities.patents.select.all.bydate"));
                    ps.setDate(1, dateSql1);
                    ps.setDate(2, dateSql2);
                    break;
                case ("RSREUPatents"):
                    ps = connection.prepareStatement(
                        ("request.activities.patents.select.bydeclarant.bydate"));
                    ps.setString(1, "РГРТУ");
                    ps.setDate(2, dateSql1);
                    ps.setDate(3, dateSql2);
                    break;
                case ("russianPatents"):
                    ps = connection
                             .prepareStatement(
                                 ("request.activities.patents.select.bytype.bydate"));
                    ps.setString(1, "патент Росcии");
                    ps.setDate(2, dateSql1);
                    ps.setDate(3, dateSql2);
                    break;
                case ("foreignPatents"):
                    ps = connection
                             .prepareStatement(
                                 ("request.activities.patents.select.bytype.bydate"));
                    ps.setString(1, "зарубежный патент");
                    ps.setDate(2, dateSql1);
                    ps.setDate(3, dateSql2);
                    break;
                case ("supportedPatents"):
                    ps = connection
                             .prepareStatement(
                                 ("request.activities.patents.select.bytype.bydate"));
                    ps.setString(1, "поддерживаемый патент");
                    ps.setDate(2, dateSql1);
                    ps.setDate(3, dateSql2);
                    break;
                case ("allEvents"):
                    ps = connection.prepareStatement(
                        ("request.activities.events.select.all.bydate"));
                    ps.setDate(1, dateSql1);
                    ps.setDate(2, dateSql2);
                    break;
                case ("allExhibitions"):
                    ps = connection
                             .prepareStatement(
                                 ("request.activities.events.select.bytype.bydate"));
                    ps.setString(1, "выставка");
                    ps.setDate(2, dateSql1);
                    ps.setDate(3, dateSql2);
                    break;
                case ("internationalExhibitions"):
                    ps = connection.prepareStatement(
                        ("request.activities.events.select.bytype.bylocationtype.bydate"));
                    ps.setString(1, "выставка");
                    ps.setString(2, "международная");
                    ps.setDate(3, dateSql1);
                    ps.setDate(4, dateSql2);
                    break;
                case ("allRussianExhibitions"):
                    ps = connection.prepareStatement(
                        ("request.activities.events.select.bytype.bylocationtype.bydate"));
                    ps.setString(1, "выставка");
                    ps.setString(2, "всероссийская");
                    ps.setDate(3, dateSql1);
                    ps.setDate(4, dateSql2);
                    break;
                case ("interregionalExhibitions"):
                    ps = connection.prepareStatement(
                        ("request.activities.events.select.bytype.bylocationtype.bydate"));
                    ps.setString(1, "выставка");
                    ps.setString(2, "межрегиональная");
                    ps.setDate(3, dateSql1);
                    ps.setDate(4, dateSql2);
                    break;
                case ("universityExhibitions"):
                    ps = connection.prepareStatement(
                        ("request.activities.events.select.bytype.bylocationtype.bydate"));
                    ps.setString(1, "выставка");
                    ps.setString(2, "на базе вуза");
                    ps.setDate(3, dateSql1);
                    ps.setDate(4, dateSql2);
                    break;
                case ("allExhibits"):
                    ps = connection.prepareStatement(
                        ("request.activities.events.select.bytype.participation.bydate"));
                    ps.setString(1, "выставка");
                    ps.setBoolean(2, true);
                    ps.setDate(3, dateSql1);
                    ps.setDate(4, dateSql2);
                    break;
                case ("internationalExhibits"):
                    ps = connection.prepareStatement(
                        ("request.activities.events.select.bytype.bylocationtype.participation.bydate"));
                    ps.setString(1, "выставка");
                    ps.setString(2, "международная");
                    ps.setBoolean(3, true);
                    ps.setDate(4, dateSql1);
                    ps.setDate(5, dateSql2);
                    break;
                case ("allRussianExhibits"):
                    ps = connection.prepareStatement(
                        ("request.activities.events.select.bytype.bylocationtype.participation.bydate"));
                    ps.setString(1, "выставка");
                    ps.setString(2, "всероссийская");
                    ps.setBoolean(3, true);
                    ps.setDate(4, dateSql1);
                    ps.setDate(5, dateSql2);
                    break;
                case ("interregionalExhibits"):
                    ps = connection.prepareStatement(
                        ("request.activities.events.select.bytype.bylocationtype.participation.bydate"));
                    ps.setString(1, "выставка");
                    ps.setString(2, "межрегиональная");
                    ps.setBoolean(3, true);
                    ps.setDate(4, dateSql1);
                    ps.setDate(5, dateSql2);
                    break;
                case ("universityExhibits"):
                    ps = connection.prepareStatement(
                        ("request.activities.events.select.bytype.bylocationtype.participation.bydate"));
                    ps.setString(1, "выставка");
                    ps.setString(2, "на базе вуза");
                    ps.setBoolean(3, true);
                    ps.setDate(4, dateSql1);
                    ps.setDate(5, dateSql2);
                    break;
                case ("allConferences"):
                    ps = connection
                             .prepareStatement(
                                 ("request.activities.events.select.bytype.bydate"));
                    ps.setString(1, "конференция");
                    ps.setDate(2, dateSql1);
                    ps.setDate(3, dateSql2);
                    break;
                case ("internationalConferences"):
                    ps = connection.prepareStatement(
                        ("request.activities.events.select.bytype.bylocationtype.bydate"));
                    ps.setString(1, "конференция");
                    ps.setString(2, "международная");
                    ps.setDate(3, dateSql1);
                    ps.setDate(4, dateSql2);
                    break;
                case ("allRussianConferences"):
                    ps = connection.prepareStatement(
                        ("request.activities.events.select.bytype.bylocationtype.bydate"));
                    ps.setString(1, "конференция");
                    ps.setString(2, "всероссийская");
                    ps.setDate(3, dateSql1);
                    ps.setDate(4, dateSql2);
                    break;
                case ("interregionalConferences"):
                    ps = connection.prepareStatement(
                        ("request.activities.events.select.bytype.bylocationtype.bydate"));
                    ps.setString(1, "конференция");
                    ps.setString(2, "межрегиональная");
                    ps.setDate(3, dateSql1);
                    ps.setDate(4, dateSql2);
                    break;
                case ("universityConferences"):
                    ps = connection.prepareStatement(
                        ("request.activities.events.select.bytype.bylocationtype.bydate"));
                    ps.setString(1, "конференция");
                    ps.setString(2, "на базе вуза");
                    ps.setDate(3, dateSql1);
                    ps.setDate(4, dateSql2);
                    break;
                case ("allReportsThesises"):
                    ps = connection.prepareStatement(
                        ("request.activities.events.select.bytype.participation.bydate"));
                    ps.setString(1, "конференция");
                    ps.setBoolean(2, true);
                    ps.setDate(3, dateSql1);
                    ps.setDate(4, dateSql2);
                    break;
                case ("internationalReportsThesises"):
                    ps = connection.prepareStatement(
                        ("request.activities.events.select.bytype.bylocationtype.participation.bydate"));
                    ps.setString(1, "конференция");
                    ps.setString(2, "международная");
                    ps.setBoolean(3, true);
                    ps.setDate(4, dateSql1);
                    ps.setDate(5, dateSql2);
                    break;
                case ("allRussianReportsThesises"):
                    ps = connection.prepareStatement(
                        ("request.activities.events.select.bytype.bylocationtype.participation.bydate"));
                    ps.setString(1, "конференция");
                    ps.setString(2, "всероссийская");
                    ps.setBoolean(3, true);
                    ps.setDate(4, dateSql1);
                    ps.setDate(5, dateSql2);
                    break;
                case ("interregionalReportsThesises"):
                    ps = connection.prepareStatement(
                        ("request.activities.events.select.bytype.bylocationtype.participation.bydate"));
                    ps.setString(1, "конференция");
                    ps.setString(2, "межрегиональная");
                    ps.setBoolean(3, true);
                    ps.setDate(4, dateSql1);
                    ps.setDate(5, dateSql2);
                    break;
                case ("universityReportsThesises"):
                    ps = connection.prepareStatement(
                        ("request.activities.events.select.bytype.bylocationtype.participation.bydate"));
                    ps.setString(1, "конференция");
                    ps.setString(2, "на базе вуза");
                    ps.setBoolean(3, true);
                    ps.setDate(4, dateSql1);
                    ps.setDate(5, dateSql2);
                    break;
                case ("allAwards"):
                    ps = connection.prepareStatement(
                        ("request.activities.awards.select.all.bydate"));
                    ps.setDate(1, dateSql1);
                    ps.setDate(2, dateSql2);
                    break;
                case ("internationalAwards"):
                    ps = connection
                             .prepareStatement(
                                 ("request.activities.awards.select.byvalue.bydate"));
                    ps.setString(1, "международный уровень");
                    ps.setDate(2, dateSql1);
                    ps.setDate(3, dateSql2);
                    break;
                case ("federalAwards"):
                    ps = connection
                             .prepareStatement(
                                 ("request.activities.awards.select.byvalue.bydate"));
                    ps.setString(1, "федеральный уровень");
                    ps.setDate(2, dateSql1);
                    ps.setDate(3, dateSql2);
                    break;
                case ("regionalAwards"):
                    ps = connection
                             .prepareStatement(
                                 ("request.activities.awards.select.byvalue.bydate"));
                    ps.setString(1, "региональный уровень");
                    ps.setDate(2, dateSql1);
                    ps.setDate(3, dateSql2);
                    break;
                case ("prizeAwards"):
                    ps = connection
                             .prepareStatement(
                                 ("request.activities.awards.select.bytype.bydate"));
                    ps.setString(1, "премия");
                    ps.setDate(2, dateSql1);
                    ps.setDate(3, dateSql2);
                    break;
                case ("diplomaAwards"):
                    ps = connection
                             .prepareStatement(
                                 ("request.activities.awards.select.bytype.bydate"));
                    ps.setString(1, "диплом");
                    ps.setDate(2, dateSql1);
                    ps.setDate(3, dateSql2);
                    break;
                case ("medalAwards"):
                    ps = connection
                             .prepareStatement(
                                 ("request.activities.awards.select.bytype.bydate"));
                    ps.setString(1, "медаль");
                    ps.setDate(2, dateSql1);
                    ps.setDate(3, dateSql2);
                    break;
                default:
                    break;
            }
            
            rs = ps.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return rs;
    }
    
    @Override
    public List<ScientificActivity> getSmallReportActivities(String reportType,
                                                             String dateFrom,
                                                             String dateTo) {
        List<ScientificActivity> lst = new ArrayList<ScientificActivity>();
        
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            
            if (dateFrom.isEmpty() || dateTo.isEmpty()) {
                rs = getFilledRequestWithoutDate(reportType);
            } else {
                rs = getFilledRequestWithDate(reportType, dateFrom, dateTo);
            }
            
            lst = formActivitiesList(rs);
            
            if (lst.size() > 0) {
                System.out.println(lst);
            } else {
                System.out.println("Not found");
            }
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else {
                System.err.println("Statement не создан");
            }
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else {
                System.err.println(
                    "ошибка во время чтения из БД (Список всей деятельности)");
            }
        }
        
        return lst;
    }
}
