#Please provide one file containing the test data, directly followed by the solution data (input + output)

import os, platform, time

DRAW_WIDTH = 2
DRAW_HEIGHT = 1

WORM_LENGTH = 6
OOB_OFFSET = WORM_LENGTH - 1

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


def colour_match(x: int, y: int, body_i: int , board: list[list[str]]) -> str:
    return int(y >= 0 and y < len(board) and x >= 0 and x < len(board[0]) and ((board[y][x] == "blue") == (body_i < 3)))

score = 0
for move_i, move in enumerate(moves):
    turn_i, turn_dir = [-1, "r"] if len(move) == 1 else move
    turn_multiplier = [-1, 1][turn_dir == "r"]
    sub_score = 0
    for worm_i, worm in enumerate(worms):
        if move_i in L:
            if len(move) != 1:
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
                score = -1
            worms[worm_i].insert(0, new_pos)
    if score == -1:
        break
    score += sub_score
print(score)