package com.sevar.Hotel.Booking.App.serviceImpl;

import com.sevar.Hotel.Booking.App.exception.InternalServerException;
import com.sevar.Hotel.Booking.App.exception.ResourceNotFoundException;
import com.sevar.Hotel.Booking.App.model.Room;
import com.sevar.Hotel.Booking.App.repository.RoomRepository;
import com.sevar.Hotel.Booking.App.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class RoomServiceIMPL implements RoomService {

    @Autowired
    private RoomRepository repository;

    @Override
    public Room addNewRoom(MultipartFile file, String roomType, BigDecimal roomPrice) throws SQLException, IOException {
        Room room = new Room();
        room.setRoomType(roomType);
        room.setRoomPrice(roomPrice);
        if (!file.isEmpty()) {
            byte[] photoBytes = file.getBytes();
            Blob photoBlob = new SerialBlob(photoBytes);
            room.setPhoto(photoBlob);
        }
        return repository.save(room);
    }

    @Override
    public List<String> getAllRoomTypes() {
        return repository.findDistinctRoomTypes();
    }

    @Override
    public List<Room> getAllRooms() {
        return repository.findAll();
    }

    @Override
    public byte[] getRoomPhotoByRoomId(Long roomId) throws SQLException {
        Optional<Room> theRoom = repository.findById(roomId);
        if (theRoom.isEmpty())
            throw new ResourceNotFoundException("Sorry, Room Not Found");
        Blob photoBlob = theRoom.get().getPhoto();
        if (photoBlob != null){
            return photoBlob.getBytes(1,(int) photoBlob.length());
        }
        return null;
    }

    @Override
    public void deleteRoom(Long roomId) {
        Optional<Room> theRoom = repository.findById(roomId);
        if (theRoom.isPresent())
            repository.deleteById(roomId);
    }

    @Override
    public Room updateRoom(Long roomId, String roomType, BigDecimal roomPrice, byte[] photoBytes) {
        Room room = repository.findById(roomId).orElseThrow(() -> new ResourceNotFoundException("Room Not Found"));
        if (roomType != null)room.setRoomType(roomType);
        if (roomPrice != null)room.setRoomPrice(roomPrice);
        if (photoBytes != null && photoBytes.length > 0){
            try {
                room.setPhoto(new SerialBlob(photoBytes));
            }catch (SQLException ex){
                throw new InternalServerException("Error While Updating Room");
            }
        }
        return repository.save(room);
    }

    @Override
    public Optional<Room> getRoomById(Long roomId) {
        return Optional.of(repository.findById(roomId).get());
    }

    @Override
    public List<Room> getAvailableRooms(LocalDate checkInDate, LocalDate checkOutDate, String roomType) {
        return repository.findAvailableRoomsByDatesAndType(checkInDate,checkOutDate,roomType);
    }
}
