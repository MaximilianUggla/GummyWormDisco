import os, random, platform
from typing import Callable
from typing import TypeVar

T = TypeVar("parsed value")
TEST_DIRECTORY = "tests"
DIRECTIONS = [(0,1),(1,0),(0,-1),(-1,0)]
WORM_LENGTH = 6
OOB_OFFSET = WORM_LENGTH - 1
COLORS = {
    "black":   "\033[40m",
    "red":     "\033[41m",
    "blue":    "\033[44m",
    "bright red": "\033[101m",
    "cyan":    "\033[46m",
    "pink":    "\033[105m",
    "reset":   "\033[0m",
}
DRAW_WIDTH = 2
DRAW_HEIGHT = 1

def retry_until_parse(text: str, parser: Callable[[str], T], requirements: list[Callable[[T], bool]] = []) -> T:
    while True:
        try:
            parsed_value = parser(input(text))
            if all(requirement(parsed_value) for requirement in requirements):
                return parsed_value
        except ValueError as _:
            pass
        except EOFError as _:
            return

def retry_until_expected(text: str, expected: list[str]) -> str:
    while True:
        response = input(text)
        if(response in expected):
            return response

def move_cursor(col: int, row: int) -> None:
    print(f"\033[{col};{row}H", end="")

def clear_screen() -> None:
    """Clear the terminal screen on any platform."""
    if platform.system() == "Windows":
        os.system("cls")
    else:
        os.system("clear")

def in_range(x: int, y: int, board: list[list[str]]) -> bool:
    return y >= -OOB_OFFSET and y < len(board) + OOB_OFFSET and x >= -OOB_OFFSET and x < len(board[0]) + OOB_OFFSET

def draw_cell(x: int, y: int, color: str, char: str = " ") -> None:
    ansi_color = COLORS.get(color, COLORS["reset"])
    term_row = x * DRAW_WIDTH + 1
    term_col = y * DRAW_HEIGHT + 1
    for i in range(DRAW_HEIGHT):
        move_cursor(term_col + i, term_row)
        print(f"{ansi_color}{char * DRAW_WIDTH}{COLORS['reset']}", end="", flush=True)

def print_initial_state(board, worms) -> None:
    for y, row in enumerate(board):
        for x, colour in enumerate(row):
            draw_cell(OOB_OFFSET + x,OOB_OFFSET +  y, "red" if colour == "R" else "blue")
    for worm in worms:
        for i, (x, y) in enumerate(worm):
            if(in_range(x, y, board)):
                draw_cell(OOB_OFFSET + x,OOB_OFFSET + y, "cyan" if i < 3 else "pink", "H" if i == 0 else "T" if i == 5 else " ")
        
#Please do not complain about my lazy implementation
def place_worm_until_possible(board, width, height):
    retries = 8
    free = False
    x,y = [-1,-1]
    points = []
    while retries > 0:
        while not free:
            x = random.randint(0, width - 1)
            y = random.randint(0, height - 1)
            if not board[OOB_OFFSET + y][OOB_OFFSET + x]:
                board[OOB_OFFSET + y][OOB_OFFSET + x] = True
                break
        points = [(x,y)]
        for i in range(WORM_LENGTH - 1):
            x,y = points[i]
            unused_directions = list.copy(DIRECTIONS)
            while len(unused_directions) != 0:
                dy, dx = unused_directions.pop(random.randint(0, len(unused_directions) - 1))
                if not board[OOB_OFFSET + y + dy][OOB_OFFSET + x + dx]:
                    board[OOB_OFFSET + y + dy][OOB_OFFSET + x + dx] = True
                    points.append((x + dx, y + dy))
                    break
            if len(unused_directions) == 0:
                retries -= 1
                break
        if len(points) == WORM_LENGTH:
            break
    if retries == 0:
        return None
    else:
        return points
        

if not os.path.isdir(TEST_DIRECTORY):
    print("Directory 'tests' missing.")
    print("Creating directory 'tests'...")
    os.mkdir(TEST_DIRECTORY)

while True:
    width = retry_until_parse("Enter width: ", int)
    height = retry_until_parse("Enter height: ", int)
    nbr_worms = retry_until_parse("Enter number of worms: ", int)
    nbr_lights = retry_until_parse("Enter number of lights: ", int, [lambda v: v * 6 <= width * height])
    light_max = retry_until_parse("Enter the maximum amount of rounds: ", int)

    output = []
    output.append(" ".join(map(str, [width, height])))
    output.append(" ".join([str(nbr_lights), *map(str, sorted(random.sample(range(light_max), nbr_lights)))]))
    output.append(str(nbr_worms))
    board = []

    for _ in range(height):
        row = ["RB"[random.getrandbits(1)] for _ in range(width)]
        board.append(row)
        output.append(" ".join(row))

    try:
        while True:
            clear_screen()
            board_worms = []
            for _ in range(2 * OOB_OFFSET + height):
                board_worms.append([False for _ in range(2 * OOB_OFFSET + width)])
            worms = []
            for _ in range(nbr_worms):
                worm = place_worm_until_possible(board_worms, width, height)
                if worm == None:
                    break
                else:
                    worms.append(worm)
            if len(worms) != nbr_worms:
                print("Unable to place all worms. Please try again")
                continue
            clear_screen()
            print_initial_state(board, worms)
            move_cursor(len(board_worms) + 1, 0)
            print("\n".join(output))
            if retry_until_expected("Is this acceptable? [y,n] ", ["y", "n"]) == "y":
                output.extend(" ".join(",".join(map(str,coords)) for coords in worm) for worm in worms)
                name = input("Please name the test: ")
                with open(f"{TEST_DIRECTORY}/{name}.in", "w") as file:
                    file.write("\n".join(output))
                print(f"File saved as {name}.in under {TEST_DIRECTORY}/")
                break
    except:
        break
    if retry_until_expected("Create more tests? [y,n] ", ["y", "n"]) == "n":
        break