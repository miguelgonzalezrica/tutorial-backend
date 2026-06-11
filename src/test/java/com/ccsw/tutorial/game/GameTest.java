package com.ccsw.tutorial.game;

import com.ccsw.tutorial.author.AuthorServiceImpl;
import com.ccsw.tutorial.author.model.Author;
import com.ccsw.tutorial.author.model.AuthorDto;
import com.ccsw.tutorial.author.AuthorService;
import com.ccsw.tutorial.category.CategoryService;
import com.ccsw.tutorial.category.CategoryServiceImpl;
import com.ccsw.tutorial.category.model.Category;
import com.ccsw.tutorial.category.model.CategoryDto;
import com.ccsw.tutorial.game.model.Game;
import com.ccsw.tutorial.game.model.GameDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class GameTest {

    @Mock
    private GameRepository gameRepository;

    @Mock
    private AuthorService authorService;

    @Mock
    private CategoryService categoryService;

    @InjectMocks
    private GameServiceImpl gameService;

    public static final Long NOT_EXISTS_GAME_ID = 0L;

    @Test
    public void getExistsGameIdShouldReturnGame() {

        Game game = mock(Game.class);
        when(game.getId()).thenReturn(EXISTS_GAME_ID);
        when(gameRepository.findById(EXISTS_GAME_ID)).thenReturn(Optional.of(game));

        Game gameResponse = gameService.get(EXISTS_GAME_ID);

        assertNotNull(gameResponse);
        assertEquals(EXISTS_GAME_ID, game.getId());
    }

    @Test
    public void getNotExistsGameIdShouldReturnNull() {

        when(gameRepository.findById(NOT_EXISTS_GAME_ID)).thenReturn(Optional.empty());

        Game game = gameService.get(NOT_EXISTS_GAME_ID);

        assertNull(game);
    }

    @Test
    public void findAllShouldReturnAllGames() {

        List<Game> list = new ArrayList<>();
        list.add(mock(Game.class));

        when(gameRepository.findAll(any())).thenReturn(list);

        List<Game> games = gameService.find(null, null);

        assertNotNull(games);
        assertEquals(1, games.size());
    }

    public static final String GAME_TITLE = "Catan II";


    private GameDto createGameDto() {

        GameDto gameDto = new GameDto();
        gameDto.setTitle(GAME_TITLE);

        AuthorDto author = new AuthorDto();
        author.setId(1L);

        CategoryDto category = new CategoryDto();
        category.setId(1L);

        gameDto.setAuthor(author);
        gameDto.setCategory(category);

        return gameDto;
    }


    @Test
    public void saveNotExistsGameIdShouldInsert() {

        GameDto gameDto = createGameDto();
        ArgumentCaptor<Game> game = ArgumentCaptor.forClass(Game.class);

        Author author = new Author();
        author.setId(1L);
        Category category = new Category();
        category.setId(1L);
        when(authorService.get(anyLong())).thenReturn(author);
        when(categoryService.get(anyLong())).thenReturn(category);

        gameService.save(null, gameDto);

        verify(gameRepository).save(game.capture());

        assertEquals(GAME_TITLE, game.getValue().getTitle());
    }

    public static final Long EXISTS_GAME_ID = 1L;

    @Test
    public void saveExistsGameIdShouldUpdate() {

        GameDto gameDto = createGameDto();

        Game game = new Game();

        when(gameRepository.findById(EXISTS_GAME_ID)).thenReturn(Optional.of(game));

        gameService.save(EXISTS_GAME_ID, gameDto);

        verify(gameRepository).save(game);
    }
}