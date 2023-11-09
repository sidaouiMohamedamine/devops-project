package tn.esprit.spring.services;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import tn.esprit.spring.entities.*;
import tn.esprit.spring.repositories.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class SkierServicesImplTest {

        @Mock
        private ISkierRepository skierRepository;

        @Mock
        private ICourseRepository courseRepository;

        @Mock
        private IRegistrationRepository registrationRepository;

        @Mock
        private ISubscriptionRepository subscriptionRepository;

        @Mock
        private IPisteRepository pisteRepository;

        @InjectMocks
        private SkierServicesImpl skierServices;






    @Test
    void retrieveAllSkiers() {

        Long idS=1L;
        Long idL=2L;
        Long idR=3L;

        Skier skier1 = Skier
                .builder()
                 .numSkier(idS)
                .firstName("Sidaoui")
                .lastName("Mohamed Amine")
                .dateOfBirth(null)
                .city("Bouhajla")
                .build();

        Skier skier2 = Skier
                .builder()
                .numSkier(idL)
                .firstName("Layth")
                .lastName("Ghndri")
                .dateOfBirth(null)
                .city("jerba")
                .build();
        Skier skier3 = Skier
                .builder()
                .numSkier(idR)
                .firstName("Wechteti")
                .lastName("Ramez")
                .dateOfBirth(null)
                .city("Tunis")
                .build();
        List<Skier> skierList = Arrays.asList(skier1, skier2,skier3);

        when(skierRepository.findAll()).thenReturn(skierList);

        List<Skier> result = skierServices.retrieveAllSkiers();

        Assertions.assertThat(result).isNotNull();

    }


    @Test
    public void testAddSkier() {


        Skier skier = Skier
                .builder()
                .numSkier(1L)
                .firstName("Sidaoui")
                .lastName("Mohamed Amine")
                .dateOfBirth(null)
                .city("Bouhajla")
                .build();

        when(skierRepository.save(Mockito.any(Skier.class))).thenReturn(skier);

        Skier savedSkier = skierServices.addSkier(skier);

        Assertions.assertThat(savedSkier).isNotNull();
    }
    @Test
    void assignSkierToSubscription() {
        Long numSkier = 1L;
        Long numSubscription = 2L;

        // Création d'un objet skier
        Skier skier = new Skier();
        skier.setNumSkier(numSkier);

        // Création d'un objet subscription
        Subscription subscription = new Subscription();
        subscription.setNumSub(numSubscription);

        // Mocker le comportement des repositories
        when(skierRepository.findById(numSkier)).thenReturn(java.util.Optional.of(skier));
        when(subscriptionRepository.findById(numSubscription)).thenReturn(java.util.Optional.of(subscription));
        when(skierRepository.save(skier)).thenReturn(skier);

        // Exécuter la méthode assignSkierToSubscription
        Skier updatedSkier = skierServices.assignSkierToSubscription(numSkier, numSubscription);

        // Vérifier que la méthode findById a été appelée avec les bons IDs
        verify(skierRepository, times(1)).findById(numSkier);
        verify(subscriptionRepository, times(1)).findById(numSubscription);

        // Vérifier que la méthode save a été appelée avec le bon skieur
        verify(skierRepository, times(1)).save(skier);

        // Vérifier que le skieur retourné est le même que celui passé en argument
        assertEquals(updatedSkier, skier);
    }

    @Test
    void addSkierAndAssignToCourse() {

        // Création d'un objet skier
        Skier skier = new Skier();
        skier.setRegistrations(new HashSet<>());

        // Création de l'objet Course
        Course course = new Course();
        course.setNumCourse(1L);

        // Mocker le comportement du SkierRepository
        when(skierRepository.save(skier)).thenReturn(skier);

        // Mocker le comportement du CourseRepository
        when(courseRepository.getById(1L)).thenReturn(course);

        // Exécution de la méthode addSkierAndAssignToCourse
        Skier savedSkier = skierServices.addSkierAndAssignToCourse(skier, 1L); // Associez le skieur au cours avec l'ID 1L

        // vérifier que la méthode save a été appelée avec le bon skieur
        verify(skierRepository, times(1)).save(skier);

        // Vérifier que la méthode getById a été appelée avec le bon ID de cours
        verify(courseRepository, times(1)).getById(1L);

        // Vérifier que la méthode save a été appelée pour chaque inscription
        verify(registrationRepository, times(skier.getRegistrations().size())).save(any(Registration.class));

        // Vérifier que le skieur retourné est bien le skieur sauvegardé
        assertEquals(savedSkier, skier);
    }

    @Test
    void removeSkier() {
        Long skierIdToDelete = 1L; // c'est l'id de skier qu'on veut supprimer

        // Appelle de  la méthode removeSkier
        skierServices.removeSkier(skierIdToDelete);

        // Vérifiez que la méthode deleteById a été appelée avec le bon ID de skieur
        verify(skierRepository, times(1)).deleteById(skierIdToDelete);

    }

    @Test
    void retrieveSkier() {
        Long idS=1L;
        // Création de l'objet skier
        Skier skier = Skier
                .builder()
                .numSkier(idS)
                .firstName("Sidaoui")
                .lastName("Mohamed Amine")
                .dateOfBirth(null)
                .city("Bouhajla")
                .build();
        // Appelle de la méthode retreive skier
        when(skierRepository.findById(1L)).thenReturn(Optional.ofNullable(skier));
        Skier retrivedSkier = skierServices.retrieveSkier(1L);

         // Pour assurer que le skier récupéré n'est pas null
        Assertions.assertThat(retrivedSkier).isNotNull();
        // Pour assurer que le  skier récupéré et celui demandé
        Assertions.assertThat(retrivedSkier.getNumSkier()).isEqualTo(skier.getNumSkier());

    }

    @Test
    void assignSkierToPiste() {
        // Création de l'objet skier
        Skier skier = new Skier();
        skier.setNumSkier(1L);
        //Création de l'objet piste
        Piste piste = new Piste();
        piste.setNumPiste(1L);

        // Configurer le comportement des repositories
        when(skierRepository.findById(1L)).thenReturn(Optional.of(skier));
        when(pisteRepository.findById(1L)).thenReturn(Optional.of(piste));

        // Applelle de  la méthode assignSkierToPiste
        Skier updatedSkier = skierServices.assignSkierToPiste(1L, 1L);


        // Vérifiez que SkierRepository.save a été appelé
        verify(skierRepository, times(1)).save(skier);

    }

    @Test
    void retrieveSkiersBySubscriptionType() {
        TypeSubscription typeSubscription = TypeSubscription.ANNUAL;

        Skier skier1 = new Skier();
        skier1.setNumSkier(1L);
        skier1.setFirstName("John");
        skier1.setLastName("Doe");

        Skier skier2 = new Skier();
        skier2.setNumSkier(2L);
        skier2.setFirstName("Jane");
        skier2.setLastName("Smith");

        List<Skier> skiers = Arrays.asList(skier1, skier2);

        // Configurer le comportement du repository
        when(skierRepository.findBySubscription_TypeSub(typeSubscription)).thenReturn(skiers);

        // Appelle de  la méthode retrieveSkiersBySubscriptionType
        List<Skier> result = skierServices.retrieveSkiersBySubscriptionType(typeSubscription);

        // Vérifier que la méthode renvoie la liste correcte de Skiers
        assertEquals(2, result.size());
        assertEquals("John", result.get(0).getFirstName());
        assertEquals("Jane", result.get(1).getFirstName());
    }
}