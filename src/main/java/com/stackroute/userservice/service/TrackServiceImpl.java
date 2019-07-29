package com.stackroute.userservice.service;

import com.stackroute.userservice.domain.Track;
import com.stackroute.userservice.exceptions.TrackAlreadyExistsException;
import com.stackroute.userservice.exceptions.TrackNotFoundException;
import com.stackroute.userservice.repository.TrackRepository;
import io.swagger.models.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import static org.springframework.http.HttpHeaders.USER_AGENT;


//import static jdk.internal.net.http.HttpRequestImpl.USER_AGENT;

@Service
public class TrackServiceImpl implements TrackService {

    TrackRepository trackRepository;

    @Autowired
    public TrackServiceImpl(TrackRepository trackRepository) {

        this.trackRepository = trackRepository;
    }

    @Override
    public void saveTrack(Track track) throws TrackAlreadyExistsException {
        if (trackRepository.existsById(track.getId())) {
            throw new TrackAlreadyExistsException();
        }
        Track savedtrack = trackRepository.save(track);
        if (savedtrack == null) {
            throw new TrackAlreadyExistsException();
        }

    }

    @Override
    public List<Track> getAllTracks() {

        return trackRepository.findAll();
    }

    //    @Override
//    public Track addNewTrack(Track track)
//    {
//        return trackRepository.save(track);
//    }
    @Override
    public Track updateTrack(Track track) throws TrackNotFoundException {
        if (trackRepository.existsById(track.getId())) {
            Track track1 = trackRepository.findById(track.getId()).get();
            track1.setComment(track.getComment());
            trackRepository.save(track1);
            return track1;
        } else {
            throw new TrackNotFoundException();
        }

        //return trackRepository.save(track);
    }

    @Override
    public void deleteTrackById(int id) {

        trackRepository.deleteById(id);
    }

    @Override
    public List<Track> trackByName(String name) {

        return trackRepository.trackByName(name);
    }


    @Override
    public String getUrlData() throws Exception {
        String url = "http://ws.audioscrobbler.com/2.0/?method=chart.gettoptracks&api_key=60318cf025cbe9289ee3d0b42692abc2&format=json";
        String message = "";
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("User-Agent", USER_AGENT);
        int responseCode = con.getResponseCode();
        System.out.println("GET Response Code :: " + responseCode);
        if (responseCode == HttpURLConnection.HTTP_OK) {
            // success
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            // print result
            message = response.toString();
        } else {
            message = "GET request not worked";
        }
        //System.out.println(message);

        return message;
    }
}
