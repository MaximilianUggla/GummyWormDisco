#Please provide one file containing the test data, directly followed by the solution data (input + output)

import os, platform, time

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

WORM_LENGTH = 6
OOB_OFFSET = WORM_LENGTH - 1

def clear_screen() -> None:
    """Clear the terminal screen on any platform."""
    if platform.system() == "Windows":
        os.system("cls")
    else:
        os.system("clear")

def move_cursor(col: int, row: int) -> None:
    print(f"\033[{col};{row}H", end="")

def convert_to_text(move_i, move, lights):
    if move_i in lights:
        return ["Paused according to the lights", ""] if len(move) == 1 else ["Moved during spotlight flash", "red"] #Case should not be reachable
    else:
        if len(move) == 1:
            return ["Made no moves", ""]  
        else:
            worm_i, dir = move
            return [f"Moved the {worm_i}{number_cardinal(int(worm_i))} worm to the {["left", "right"][dir == "r"]}", ""]

def number_cardinal(num):
    if num % 100 in [11, 12, 13]:
        return "th"
    elif num % 10 == 1:
        return "st"
    elif num % 10 == 2:
        return "nd"
    elif num % 10 == 3:
        return "rd"
    else:
        return "th"

def draw_cell(x: int, y: int, color: str) -> None:
    ansi_color = COLORS.get(color, COLORS["reset"])
    term_row = x * DRAW_WIDTH + 1
    term_col = y * DRAW_HEIGHT + 1
    for i in range(DRAW_HEIGHT):
        move_cursor(term_col + i, term_row)
        print(f"{ansi_color}{' ' * DRAW_WIDTH}{COLORS['reset']}", end="", flush=True)

def draw_text(x: int, y: int, color: str, text: str) -> None:
    ansi_color = COLORS.get(color, COLORS["reset"])
    term_row = x * DRAW_WIDTH + 1
    term_col = y * DRAW_HEIGHT + 1
    for i in range(DRAW_HEIGHT):
        move_cursor(term_col + i, term_row)
        print(f"{ansi_color}{text}{COLORS['reset']}", end="", flush=True)

def cprint(text: str, color: str) -> None:
    code = COLORS.get(color.lower(), COLORS["reset"])
    print(f"{code}{text}{COLORS['reset']}")

def inrange_or_black(x: int, y: int, board: list[list[str]]) -> str:
    if y >= 0 and y < len(board) and x >= 0 and x < len(board[0]):
        return board[y][x]
    else:
        return "black"

def worm_overlap(self_i, y, x, others):
    for i, other in enumerate(others):
        if i != self_i:
            for oy, ox in other:
                if y == oy and x == ox:
                    return True
    return False


W, H = map(int, input().split())
T, *L = map(int, input().split())
nbr_moves = L[-1]
N = int(input())
board = []
for _ in range(H):
    board.append(["blue" if colour == "B" else "red" for colour in input().split()])
worms = []
for _ in range(N):
    pairs = input().split()
    worms.append(list(list(map(int, pair.split(","))) for pair in pairs))

moves = [input().split() for _ in range(nbr_moves + 1)]

picture = []
for _ in range(OOB_OFFSET):
    picture.append(["black" for _ in range(2 * OOB_OFFSET + W)])
for y in range(H):
    line = []
    line.extend(["black" for _ in range(OOB_OFFSET)])
    line.extend(board[y])
    line.extend(["black" for _ in range(OOB_OFFSET)])
    picture.append(line)
for _ in range(OOB_OFFSET):
    picture.append(["black" for _ in range(2 * OOB_OFFSET + W)])


for worm in worms:
    for i, (x,y) in enumerate(worm):
        picture[OOB_OFFSET + y][OOB_OFFSET + x] = "cyan" if i < 3 else "pink"

clear_screen()
for y, row in enumerate(picture):
    for x, colour in enumerate(row):
        draw_cell(x, y, colour)


def colour_match(x: int, y: int, body_i: int , board: list[list[str]]) -> str:
    return int(y >= 0 and y < len(board) and x >= 0 and x < len(board[0]) and ((board[y][x] == "blue") == (body_i < 3)))

nbr_of_text_prints = 0
score = 0
for move_i, move in enumerate(moves):
    time.sleep(1)
    turn_i, turn_dir = [-1, "r"] if len(move) == 1 else move
    turn_multiplier = [-1, 1][turn_dir == "r"]
    sub_score = 0
    for worm_i, worm in enumerate(worms):
        if move_i in L:
            if len(move) != 1:
                draw_text(OOB_OFFSET * 2 + W, nbr_of_text_prints, "red", "Moved when spotlights are flashing")
                nbr_of_text_prints += 1
                score = -1
                break
            for body_i, (x, y) in enumerate(worm):
                sub_score += colour_match(x, y, body_i, board)
        else:
            worm_dir_y = worm[1][0] - worm[0][0]
            worm_dir_x = worm[1][1] - worm[0][1]
            xo, yo = worms[worm_i].pop()
            new_pos = ()
            if int(turn_i) == worm_i:
                new_pos = (worm[0][0] + worm_dir_x * turn_multiplier, worm[0][1] - worm_dir_y * turn_multiplier)
            else:
                new_pos = (worm[0][0] - worm_dir_y, worm[0][1] - worm_dir_x)
            if worm_overlap(worm_i, *new_pos, worms):
                draw_text(OOB_OFFSET * 2 + W, nbr_of_text_prints, "red", "Worms have collided")
                nbr_of_text_prints += 1
                score = -2
            worms[worm_i].insert(0, new_pos)
            draw_cell(OOB_OFFSET + xo, OOB_OFFSET + yo, inrange_or_black(xo, yo, board))
    if score == -1:
        break
    for worm_i, worm in enumerate(worms):
        for i, (y, x) in enumerate(worm):
            if x < -OOB_OFFSET or y < -OOB_OFFSET or x >= (len(board[0]) + OOB_OFFSET) or y >= (len(board) + OOB_OFFSET):
                continue
            colour = "cyan" if i < 3 else "pink"
            if worm_overlap(worm_i, y, x, worms):
                colour = "bright red"
            draw_cell(OOB_OFFSET + y, OOB_OFFSET +  x, colour)
    if score == -2:
        score = -1
        break
    info_text, colour = convert_to_text(move_i, move, L)
    score += sub_score
    if move_i in L:
        info_text += f", +{sub_score}"
    draw_text(OOB_OFFSET * 2 + W, nbr_of_text_prints, colour, info_text)
    nbr_of_text_prints += 1
    draw_cell(2 * OOB_OFFSET + W - 1, 2 * OOB_OFFSET + H - 1, "black")
draw_text(OOB_OFFSET * 2 + W, nbr_of_text_prints, "", f"Total score: {score}")
nbr_of_text_prints += 1
if 2 * OOB_OFFSET + H - 1 > nbr_of_text_prints:
    draw_cell(2 * OOB_OFFSET + W - 1, (2 * OOB_OFFSET + H - 1), "black")